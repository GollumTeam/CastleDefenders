package forge;

import forge.packets.PacketHandlerBase;
import java.util.*;
import net.minecraft.src.NetworkManager;

public class MessageManager
{
    public class ConnectionInstance
    {
        private NetworkManager network;
        private Hashtable channelToHandlers;
        private Hashtable handlerToChannels;
        private HashSet activeChannels;

        public NetworkManager getNetwork()
        {
            return network;
        }

        public String[] unregisterAll()
        {
            String as[] = getRegisteredChannels();
            channelToHandlers.clear();
            handlerToChannels.clear();
            return as;
        }

        public boolean registerChannel(IPacketHandler ipackethandler, String s)
        {
            ArrayList arraylist = (ArrayList)channelToHandlers.get(s);
            ArrayList arraylist1 = (ArrayList)handlerToChannels.get(ipackethandler);
            boolean flag = false;

            if (arraylist == null)
            {
                flag = true;
                arraylist = new ArrayList();
                channelToHandlers.put(s, arraylist);
            }

            if (arraylist1 == null)
            {
                arraylist1 = new ArrayList();
                handlerToChannels.put(ipackethandler, arraylist1);
            }

            if (!arraylist1.contains(s))
            {
                arraylist1.add(s);
            }

            if (!arraylist.contains(ipackethandler))
            {
                arraylist.add(ipackethandler);
            }

            return flag;
        }

        public boolean unregisterChannel(IPacketHandler ipackethandler, String s)
        {
            boolean flag = false;
            ArrayList arraylist = (ArrayList)channelToHandlers.get(s);
            ArrayList arraylist1 = (ArrayList)handlerToChannels.get(ipackethandler);

            if (arraylist != null && arraylist.contains(ipackethandler))
            {
                arraylist.remove(ipackethandler);

                if (arraylist.size() == 0)
                {
                    flag = true;
                    channelToHandlers.remove(s);
                }
            }

            if (arraylist1 != null && arraylist1.contains(s))
            {
                arraylist1.remove(s);

                if (arraylist.size() == 0)
                {
                    handlerToChannels.remove(ipackethandler);
                }
            }

            return flag;
        }

        public String[] unregisterHandler(IPacketHandler ipackethandler)
        {
            ArrayList arraylist = (ArrayList)handlerToChannels.get(ipackethandler);

            if (arraylist != null)
            {
                String as[] = (String[])arraylist.toArray(new String[0]);
                arraylist = new ArrayList();
                String as1[] = as;
                int i = as1.length;

                for (int j = 0; j < i; j++)
                {
                    String s = as1[j];

                    if (unregisterChannel(ipackethandler, s))
                    {
                        arraylist.add(s);
                    }
                }

                return (String[])arraylist.toArray(new String[0]);
            }
            else
            {
                return new String[0];
            }
        }

        public String[] getRegisteredChannels()
        {
            int i = 0;
            String as[] = new String[channelToHandlers.size()];

            for (Iterator iterator = channelToHandlers.keySet().iterator(); iterator.hasNext();)
            {
                String s = (String)iterator.next();
                as[i++] = s;
            }

            return as;
        }

        public IPacketHandler[] getChannelHandlers(String s)
        {
            ArrayList arraylist = (ArrayList)channelToHandlers.get(s);

            if (arraylist != null)
            {
                return (IPacketHandler[])arraylist.toArray(new IPacketHandler[0]);
            }
            else
            {
                return new IPacketHandler[0];
            }
        }

        public void addActiveChannel(String s)
        {
            if (!activeChannels.contains(s))
            {
                activeChannels.add(s);
            }
        }

        public void removeActiveChannel(String s)
        {
            if (activeChannels.contains(s))
            {
                activeChannels.remove(s);
            }
        }

        public boolean isActiveChannel(String s)
        {
            return activeChannels.contains(s);
        }

        public ConnectionInstance(NetworkManager networkmanager)
        {
            channelToHandlers = new Hashtable();
            handlerToChannels = new Hashtable();
            activeChannels = new HashSet();
            network = networkmanager;
        }
    }

    private Hashtable connections;
    private static MessageManager instance;

    public MessageManager()
    {
        connections = new Hashtable();
    }

    public static MessageManager getInstance()
    {
        if (instance == null)
        {
            instance = new MessageManager();
        }

        return instance;
    }

    public ConnectionInstance getConnection(NetworkManager networkmanager)
    {
        ConnectionInstance connectioninstance = (ConnectionInstance)connections.get(networkmanager);

        if (connectioninstance == null)
        {
            connectioninstance = new ConnectionInstance(networkmanager);
            connections.put(networkmanager, connectioninstance);
        }

        return connectioninstance;
    }

    public String[] removeConnection(NetworkManager networkmanager)
    {
        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            String as[] = connectioninstance.unregisterAll();
            connections.remove(networkmanager);
            return as;
        }
        else
        {
            return new String[0];
        }
    }

    public boolean registerChannel(NetworkManager networkmanager, IPacketHandler ipackethandler, String s)
    {
        ConnectionInstance connectioninstance = getConnection(networkmanager);
        return connectioninstance.registerChannel(ipackethandler, s);
    }

    public boolean unregisterChannel(NetworkManager networkmanager, IPacketHandler ipackethandler, String s)
    {
        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            return connectioninstance.unregisterChannel(ipackethandler, s);
        }
        else
        {
            return false;
        }
    }

    public String[] unregisterHandler(NetworkManager networkmanager, IPacketHandler ipackethandler)
    {
        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            return connectioninstance.unregisterHandler(ipackethandler);
        }
        else
        {
            return new String[0];
        }
    }

    public String[] getRegisteredChannels(NetworkManager networkmanager)
    {
        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            return connectioninstance.getRegisteredChannels();
        }
        else
        {
            return new String[0];
        }
    }

    public IPacketHandler[] getChannelHandlers(NetworkManager networkmanager, String s)
    {
        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            return connectioninstance.getChannelHandlers(s);
        }
        else
        {
            return new IPacketHandler[0];
        }
    }

    public void addActiveChannel(NetworkManager networkmanager, String s)
    {
        ConnectionInstance connectioninstance = getConnection(networkmanager);
        connectioninstance.addActiveChannel(s);
    }

    public void removeActiveChannel(NetworkManager networkmanager, String s)
    {
        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            connectioninstance.removeActiveChannel(s);
        }
    }

    public boolean isActiveChannel(NetworkManager networkmanager, String s)
    {
        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            return connectioninstance.isActiveChannel(s);
        }
        else
        {
            return false;
        }
    }

    public void dispatchIncomingMessage(NetworkManager networkmanager, String s, byte abyte0[])
    {
        if (abyte0 == null)
        {
            abyte0 = new byte[0];
        }

        if (s.equals("Forge") && ForgeHooks.getPacketHandler() != null)
        {
            byte abyte1[] = new byte[abyte0.length];
            System.arraycopy(abyte0, 0, abyte1, 0, abyte0.length);
            ForgeHooks.getPacketHandler().onPacketData(networkmanager, s, abyte1);
        }

        if (connections.containsKey(networkmanager))
        {
            ConnectionInstance connectioninstance = getConnection(networkmanager);
            IPacketHandler aipackethandler[] = connectioninstance.getChannelHandlers(s);
            byte abyte2[] = new byte[abyte0.length];
            IPacketHandler aipackethandler1[] = aipackethandler;
            int i = aipackethandler1.length;

            for (int j = 0; j < i; j++)
            {
                IPacketHandler ipackethandler = aipackethandler1[j];
                System.arraycopy(abyte0, 0, abyte2, 0, abyte0.length);
                ipackethandler.onPacketData(networkmanager, s, abyte2);
            }
        }
    }
}
