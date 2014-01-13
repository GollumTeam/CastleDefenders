package forge;

import java.util.*;
import net.minecraft.src.Achievement;

public class AchievementPage
{
    private String name;
    private LinkedList achievements;

    public AchievementPage(String s, Achievement aachievement[])
    {
        name = s;
        achievements = new LinkedList(Arrays.asList(aachievement));
    }

    public String getName()
    {
        return name;
    }

    public List getAchievements()
    {
        return achievements;
    }
}
