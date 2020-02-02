package com.fr.factoryclicker.resources;

import android.util.Log;

import com.fr.factoryclicker.recipe.RecipeElement;

public class ResourceValues {

    public static Resource getResourceValues(ResourceType rt){

        switch(rt){
            case IRON_ORE: return getIron_ore();
            case COPPER_ORE: return getCopper_ore();
            case LIMESTONE: return getLimestone();
            case COAL: return getCoal();
            case CRUDE_OIL: return getCrude_oil();
            case CATERIUM_ORE: return getCaterium_ore();
            case RAW_QUARTZ: return getRaw_quartz();
            case BAUXITE: return getBauxite();


            case CABLE: return getCable();
            case CONCRETE: return getConcrete();
            case COPPER_INGOT: return getCopper_ingot();
            case IRON_INGOT: return getIron_ingot();
            case IRON_PLATE: return getIron_plate();
            case IRON_ROD: return getIron_rod();
            case SCREW: return getScrew();
            case WIRE: return getWire();
            case CATERIUM_INGOT: return getCaterium_ingot();
            case QUICKWIRE: return getQuickwire();
            case QUARTZ_CRYSTAL: return getQuartz_crystal();
            case SILICA: return getSilica();
            case STEEL_BEAM: return getSteel_beam();
            case STEEL_PIPE: return getSteel_pipe();
            case PLASTIC: return getPlastic();
            case RUBBER: return getRubber();


            case REINFORCED_IRON_PLATE: return getReinforced_iron_plate();
            case MODULAR_FRAME: return getModular_frame();
            case ROTOR: return getRotor();
            case ENCASED_INDUSTRIAL_BEAM: return getEncased_industrial_beam();
            case MOTOR: return getMotor();
            case STATOR: return getStator();
            case STEEL_INGOT: return getSteel_ingot();
            case AI_LIMITER: return getAI_limiter();
            case CIRCUIT_BOARD: return getCircuit_board();
            case ALUMINIUM_INGOT: return getAluminium_ingot();
            case ALUMINIUM_SHEET: return getAluminium_sheet();
            case HEAT_SINK: return getHeat_sink();


            case CRYSTAL_OSCILLATOR: return getCrystal_oscillator();
            case HIGH_SPEED_CONNECTOR: return getHigh_speed_connector();


            case HEAVY_MODULAR_FRAME: return getHeavy_modular_frame();
            case COMPUTER: return getComputer();
            case SUPERCOMPUTER: return getSupercomputer();
            case RADIO_CONTROL_UNIT: return getRadio_control_unit();
            case TURBO_MOTOR: return getTurbo_motor();

            default: Log.i("BUG", "Unknown resource type : "+rt+" in ResourceValues");
                    return null;
        }

    }

    //Basic resources

    private static Resource getIron_ore(){
        return new Resource(ResourceType.IRON_ORE, 100, 1, 1, 0);
    }

    private static Resource getCopper_ore(){
        return new Resource(ResourceType.COPPER_ORE, 100, 1, 1, 0);
    }

    private static Resource getLimestone(){
        return new Resource(ResourceType.LIMESTONE, 100, 1, 1, 0);
    }

    private static Resource getCoal(){
        return new Resource(ResourceType.COAL, 100, 1, 1, 0);
    }

    private static Resource getCrude_oil(){
        return new Resource(ResourceType.CRUDE_OIL, 100, 1, 1, 0);
    }

    private static Resource getCaterium_ore(){
        return new Resource(ResourceType.CATERIUM_ORE, 100, 1, 1, 0);
    }

    private static Resource getRaw_quartz(){
        return new Resource(ResourceType.RAW_QUARTZ, 100, 1, 1, 0);
    }

    private static Resource getBauxite(){
        return new Resource(ResourceType.BAUXITE, 100, 1, 1, 0);
    }

    //Resources with one ingredient in their recipe

    private static Resource getCable(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.WIRE, 2);

        return new Resource(ResourceType.CABLE, 100, 1, 1,
                1, ingredient1);
    }

    private static Resource getConcrete(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.LIMESTONE, 3);

        return new Resource(ResourceType.CONCRETE, 100, 1, 1,
                1, ingredient1);
    }

    private static Resource getCopper_ingot(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.COPPER_ORE, 1);

        return new Resource(ResourceType.COPPER_INGOT, 100, 2, 1,
                1, ingredient1);
    }

    private static Resource getIron_ingot(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.IRON_ORE, 1);

        return new Resource(ResourceType.IRON_INGOT, 100, 2, 1,
                1, ingredient1);
    }

    private static Resource getIron_plate(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.IRON_INGOT, 2);

        return new Resource(ResourceType.IRON_PLATE, 100, 1, 1,
                1, ingredient1);
    }

    private static Resource getIron_rod(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.IRON_INGOT, 1);

        return new Resource(ResourceType.IRON_ROD, 100, 1, 1,
                1, ingredient1);
    }

    private static Resource getScrew(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.IRON_ROD, 1);

        return new Resource(ResourceType.SCREW, 500, 1, 6,
                1, ingredient1);
    }

    private static Resource getWire(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.COPPER_INGOT, 1);

        return new Resource(ResourceType.WIRE, 500, 1, 3,
                1, ingredient1);
    }

    private static Resource getCaterium_ingot(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.CATERIUM_ORE, 3);

        return new Resource(ResourceType.CATERIUM_INGOT, 100, 4, 1,
                1, ingredient1);
    }

    private static Resource getQuickwire(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.CATERIUM_INGOT, 1);

        return new Resource(ResourceType.QUICKWIRE, 500, 1, 4,
                1, ingredient1);
    }

    private static Resource getQuartz_crystal(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.RAW_QUARTZ, 2);

        return new Resource(ResourceType.QUARTZ_CRYSTAL, 100, 1, 1,
                1, ingredient1);
    }

    private static Resource getSilica(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.RAW_QUARTZ, 2);

        return new Resource(ResourceType.SILICA, 100, 1, 3,
                1, ingredient1);
    }

    private static Resource getSteel_beam(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.STEEL_INGOT, 3);

        return new Resource(ResourceType.STEEL_BEAM, 100, 2, 1,
                1, ingredient1);
    }

    private static Resource getSteel_pipe(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.STEEL_INGOT, 1);

        return new Resource(ResourceType.STEEL_PIPE, 100, 1, 1,
                1, ingredient1);
    }

    private static Resource getPlastic(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.CRUDE_OIL, 4);

        return new Resource(ResourceType.PLASTIC, 100, 2, 3,
                1, ingredient1);
    }

    private static Resource getRubber(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.CRUDE_OIL, 4);

        return new Resource(ResourceType.RUBBER, 100, 2, 4,
                1, ingredient1);
    }

    //Resources with 2 ingredients in their recipe

    private static Resource getReinforced_iron_plate(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.IRON_PLATE, 4);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.SCREW, 24);

        return new Resource(ResourceType.REINFORCED_IRON_PLATE, 100, 3, 1,
                2, ingredient1, ingredient2);
    }

    private static Resource getModular_frame(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.REINFORCED_IRON_PLATE, 3);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.IRON_ROD, 6);

        return new Resource(ResourceType.MODULAR_FRAME, 50, 4, 1,
                2, ingredient1, ingredient2);
    }

    private static Resource getRotor(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.IRON_ROD, 3);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.SCREW, 22);

        return new Resource(ResourceType.ROTOR, 100, 3, 1,
                2, ingredient1, ingredient2);
    }

    private static Resource getEncased_industrial_beam(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.STEEL_BEAM, 4);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.CONCRETE, 5);

        return new Resource(ResourceType.ENCASED_INDUSTRIAL_BEAM, 100, 4, 1,
                2, ingredient1, ingredient2);
    }

    private static Resource getMotor(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.ROTOR, 2);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.STATOR, 2);

        return new Resource(ResourceType.MOTOR, 50, 3, 1,
                2, ingredient1, ingredient2);
    }

    private static Resource getStator(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.STEEL_PIPE, 3);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.WIRE, 10);

        return new Resource(ResourceType.STATOR, 100, 3, 1,
                2, ingredient1, ingredient2);
    }

    private static Resource getSteel_ingot(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.IRON_ORE, 3);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.COAL, 3);

        return new Resource(ResourceType.STEEL_INGOT, 100, 4, 2,
                2, ingredient1, ingredient2);
    }

    private static Resource getAI_limiter(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.CIRCUIT_BOARD, 2);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.QUICKWIRE, 18);

        return new Resource(ResourceType.AI_LIMITER, 100, 3, 1,
                2, ingredient1, ingredient2);
    }

    private static Resource getCircuit_board(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.WIRE, 12);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.PLASTIC, 6);

        return new Resource(ResourceType.CIRCUIT_BOARD, 200, 3, 2,
                2, ingredient1, ingredient2);
    }

    private static Resource getAluminium_ingot(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.BAUXITE, 7);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.SILICA, 6);

        return new Resource(ResourceType.ALUMINIUM_INGOT, 100, 4, 2,
                2, ingredient1, ingredient2);
    }

    private static Resource getAluminium_sheet(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.ALUMINIUM_INGOT, 3);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.COPPER_INGOT, 2);

        return new Resource(ResourceType.ALUMINIUM_SHEET, 100, 3, 3,
                2, ingredient1, ingredient2);
    }

    private static Resource getHeat_sink(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.ALUMINIUM_SHEET, 4);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.RUBBER, 10);

        return new Resource(ResourceType.HEAT_SINK, 100, 3, 1,
                2, ingredient1, ingredient2);
    }

    //Resources with 3 ingredients in their recipe

    private static Resource getCrystal_oscillator(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.QUARTZ_CRYSTAL, 10);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.CABLE, 14);
        RecipeElement ingredient3 = new RecipeElement(ResourceType.REINFORCED_IRON_PLATE, 4);

        return new Resource(ResourceType.CRYSTAL_OSCILLATOR, 100, 8, 1,
                3, ingredient1, ingredient2, ingredient3);
    }

    private static Resource getHigh_speed_connector(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.QUICKWIRE, 40);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.CABLE, 10);
        RecipeElement ingredient3 = new RecipeElement(ResourceType.PLASTIC, 6);

        return new Resource(ResourceType.HIGH_SPEED_CONNECTOR, 100, 6, 1,
                3, ingredient1, ingredient2, ingredient3);
    }

    //Resources with 4 ingredients in their recipe

    private static Resource getHeavy_modular_frame(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.MODULAR_FRAME, 5);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.STEEL_PIPE, 15);
        RecipeElement ingredient3 = new RecipeElement(ResourceType.ENCASED_INDUSTRIAL_BEAM, 5);
        RecipeElement ingredient4 = new RecipeElement(ResourceType.SCREW, 90);

        return new Resource(ResourceType.HEAVY_MODULAR_FRAME, 50, 8, 1,
                4, ingredient1, ingredient2, ingredient3, ingredient4);
    }

    private static Resource getComputer(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.CIRCUIT_BOARD, 10);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.CABLE, 12);
        RecipeElement ingredient3 = new RecipeElement(ResourceType.PLASTIC, 18);
        RecipeElement ingredient4 = new RecipeElement(ResourceType.SCREW, 60);

        return new Resource(ResourceType.COMPUTER, 50, 8, 1,
                4, ingredient1, ingredient2, ingredient3, ingredient4);
    }

    private static Resource getSupercomputer(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.COMPUTER, 2);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.AI_LIMITER, 2);
        RecipeElement ingredient3 = new RecipeElement(ResourceType.HIGH_SPEED_CONNECTOR, 3);
        RecipeElement ingredient4 = new RecipeElement(ResourceType.PLASTIC, 21);

        return new Resource(ResourceType.SUPERCOMPUTER, 50, 8, 1,
                4, ingredient1, ingredient2, ingredient3, ingredient4);
    }

    private static Resource getRadio_control_unit(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.HEAT_SINK, 4);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.RUBBER, 24);
        RecipeElement ingredient3 = new RecipeElement(ResourceType.CRYSTAL_OSCILLATOR, 1);
        RecipeElement ingredient4 = new RecipeElement(ResourceType.COMPUTER, 1);

        return new Resource(ResourceType.RADIO_CONTROL_UNIT, 50, 6, 1,
                4, ingredient1, ingredient2, ingredient3, ingredient4);
    }

    private static Resource getTurbo_motor(){
        RecipeElement ingredient1 = new RecipeElement(ResourceType.HEAT_SINK, 4);
        RecipeElement ingredient2 = new RecipeElement(ResourceType.RADIO_CONTROL_UNIT, 2);
        RecipeElement ingredient3 = new RecipeElement(ResourceType.MOTOR, 4);
        RecipeElement ingredient4 = new RecipeElement(ResourceType.RUBBER, 40);

        return new Resource(ResourceType.TURBO_MOTOR, 50, 8, 1,
                4, ingredient1, ingredient2, ingredient3, ingredient4);
    }
}
