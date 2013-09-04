package morethantrees.flowers.flowergen;

import java.util.Random;

import morethantrees.MTJT;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMarshMarigold extends WorldGenerator
{
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int l = 0; l < 20; ++l)
        {
            int i1 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int j1 = par4;
            int k1 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);
            
            if (par1World.isAirBlock(i1, par4, k1) && (par1World.getBlockMaterial(i1 - 1, par4 - 1, k1) == Material.water || par1World.getBlockMaterial(i1 + 1, par4 - 1, k1) == Material.water || par1World.getBlockMaterial(i1, par4 - 1, k1 - 1) == Material.water || par1World.getBlockMaterial(i1, par4 - 1, k1 + 1) == Material.water))
            {
                for (int x = 0; x < 2; x++)
                {
                    if (MTJT.marshMarigold.canBlockStay(par1World, i1, j1, k1))
                    {
                        par1World.setBlock(i1, j1, k1, MTJT.marshMarigold.blockID, 0, 2);
                    }
                }
            }
        }
        
        return true;
    }
}
