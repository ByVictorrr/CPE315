package Modules;

import Instructions.Instruction;
import Instructions.RegisterInstructions.I_Instruction;
import ReferenceTables.BinaryToMnemonicTable;

import java.util.HashMap;
import java.util.Map;

public class BranchPredictor{


        private static Integer GHR[];
        private static Integer GHR_SIZE;
        private static Integer totalPredicted;
        private static Integer correctPredicted;
        private static Map <Integer, Integer> Predictor;
        static {
            Predictor= new HashMap<Integer, Integer>();
            Predictor.put(0, 0);
            Predictor.put(1,0);
            Predictor.put(2, 0);
            Predictor.put(3,0);
            Predictor.put(4,0);
            Predictor.put(5,0);
            Predictor.put(6,0);
            Predictor.put(7,0);
            Predictor.put(8,0);
            Predictor.put(9,0);
            Predictor.put(10,0);
            Predictor.put(11,0);
            Predictor.put(12,0);
            Predictor.put(13,0);
            Predictor.put(14,0);
            Predictor.put(15,0);
            Predictor.put(16,0);
            Predictor.put(17,0);
            Predictor.put(18,0);
            Predictor.put(19,0);
            Predictor.put(20,0);
            Predictor.put(21,0);
            Predictor.put(22,0);
            Predictor.put(23,0);
            Predictor.put(24,0);
            Predictor.put(25,0);
            Predictor.put(26,0);
            Predictor.put(27,0);
            Predictor.put(28,0);
            Predictor.put(29,0);
            Predictor.put(30,0);
            Predictor.put(31,0);
            Predictor.put(32,0);
            Predictor.put(33,0);
            Predictor.put(34,0);
            Predictor.put(35,0);
            Predictor.put(36,0);
            Predictor.put(37,0);
            Predictor.put(38,0);
            Predictor.put(39,0);
            Predictor.put(40,0);
            Predictor.put(41,0);
            Predictor.put(42,0);
            Predictor.put(43,0);
            Predictor.put(44,0);
            Predictor.put(45,0);
            Predictor.put(46,0);
            Predictor.put(47,0);
            Predictor.put(48,0);
            Predictor.put(49,0);
            Predictor.put(50,0);
            Predictor.put(51,0);
            Predictor.put(52,0);
            Predictor.put(53,0);
            Predictor.put(54,0);
            Predictor.put(55,0);
            Predictor.put(56,0);
            Predictor.put(57,0);
            Predictor.put(58,0);
            Predictor.put(59,0);
            Predictor.put(60,0);
            Predictor.put(61,0);
            Predictor.put(62,0);
            Predictor.put(63,0);
            Predictor.put(64,0);
            Predictor.put(65,0);
            Predictor.put(66,0);
            Predictor.put(67,0);
            Predictor.put(68,0);
            Predictor.put(69,0);
            Predictor.put(70,0);
            Predictor.put(71,0);
            Predictor.put(72,0);
            Predictor.put(73,0);
            Predictor.put(74,0);
            Predictor.put(75,0);
            Predictor.put(76,0);
            Predictor.put(77,0);
            Predictor.put(78,0);
            Predictor.put(79,0);
            Predictor.put(80,0);
            Predictor.put(81,0);
            Predictor.put(82,0);
            Predictor.put(83,0);
            Predictor.put(84,0);
            Predictor.put(85,0);
            Predictor.put(86,0);
            Predictor.put(87,0);
            Predictor.put(88,0);
            Predictor.put(89,0);
            Predictor.put(90,0);
            Predictor.put(91,0);
            Predictor.put(92,0);
            Predictor.put(93,0);
            Predictor.put(94,0);
            Predictor.put(95,0);
            Predictor.put(96,0);
            Predictor.put(97,0);
            Predictor.put(98,0);
            Predictor.put(99,0);
            Predictor.put(100,0);
            Predictor.put(101,0);
            Predictor.put(102,0);
            Predictor.put(103,0);
            Predictor.put(104,0);
            Predictor.put(105,0);
            Predictor.put(106,0);
            Predictor.put(107,0);
            Predictor.put(108,0);
            Predictor.put(109,0);
            Predictor.put(110,0);
            Predictor.put(111,0);
            Predictor.put(112,0);
            Predictor.put(113,0);
            Predictor.put(114,0);
            Predictor.put(115,0);
            Predictor.put(116,0);
            Predictor.put(117,0);
            Predictor.put(118,0);
            Predictor.put(119,0);
            Predictor.put(120,0);
            Predictor.put(121,0);
            Predictor.put(122,0);
            Predictor.put(123,0);
            Predictor.put(124,0);
            Predictor.put(125,0);
            Predictor.put(126,0);
            Predictor.put(127,0);
            Predictor.put(128,0);
            Predictor.put(129,0);
            Predictor.put(130,0);
            Predictor.put(131,0);
            Predictor.put(132,0);
            Predictor.put(133,0);
            Predictor.put(134,0);
            Predictor.put(135,0);
            Predictor.put(136,0);
            Predictor.put(137,0);
            Predictor.put(138,0);
            Predictor.put(139,0);
            Predictor.put(140,0);
            Predictor.put(141,0);
            Predictor.put(142,0);
            Predictor.put(143,0);
            Predictor.put(144,0);
            Predictor.put(145,0);
            Predictor.put(146,0);
            Predictor.put(147,0);
            Predictor.put(148,0);
            Predictor.put(149,0);
            Predictor.put(150,0);
            Predictor.put(151,0);
            Predictor.put(152,0);
            Predictor.put(153,0);
            Predictor.put(154,0);
            Predictor.put(155,0);
            Predictor.put(156,0);
            Predictor.put(157,0);
            Predictor.put(158,0);
            Predictor.put(159,0);
            Predictor.put(160,0);
            Predictor.put(161,0);
            Predictor.put(162,0);
            Predictor.put(163,0);
            Predictor.put(164,0);
            Predictor.put(165,0);
            Predictor.put(166,0);
            Predictor.put(167,0);
            Predictor.put(168,0);
            Predictor.put(169,0);
            Predictor.put(170,0);
            Predictor.put(171,0);
            Predictor.put(172,0);
            Predictor.put(173,0);
            Predictor.put(174,0);
            Predictor.put(175,0);
            Predictor.put(176,0);
            Predictor.put(177,0);
            Predictor.put(178,0);
            Predictor.put(179,0);
            Predictor.put(180,0);
            Predictor.put(181,0);
            Predictor.put(182,0);
            Predictor.put(183,0);
            Predictor.put(184,0);
            Predictor.put(185,0);
            Predictor.put(186,0);
            Predictor.put(187,0);
            Predictor.put(188,0);
            Predictor.put(189,0);
            Predictor.put(190,0);
            Predictor.put(191,0);
            Predictor.put(192,0);
            Predictor.put(193,0);
            Predictor.put(194,0);
            Predictor.put(195,0);
            Predictor.put(196,0);
            Predictor.put(197,0);
            Predictor.put(198,0);
            Predictor.put(199,0);
            Predictor.put(200,0);
            Predictor.put(201,0);
            Predictor.put(202,0);
            Predictor.put(203,0);
            Predictor.put(204,0);
            Predictor.put(205,0);
            Predictor.put(206,0);
            Predictor.put(207,0);
            Predictor.put(208,0);
            Predictor.put(209,0);
            Predictor.put(210,0);
            Predictor.put(211,0);
            Predictor.put(212,0);
            Predictor.put(213,0);
            Predictor.put(214,0);
            Predictor.put(215,0);
            Predictor.put(216,0);
            Predictor.put(217,0);
            Predictor.put(218,0);
            Predictor.put(219,0);
            Predictor.put(220,0);
            Predictor.put(221,0);
            Predictor.put(222,0);
            Predictor.put(223,0);
            Predictor.put(224,0);
            Predictor.put(225,0);
            Predictor.put(226,0);
            Predictor.put(227,0);
            Predictor.put(228,0);
            Predictor.put(229,0);
            Predictor.put(230,0);
            Predictor.put(231,0);
            Predictor.put(232,0);
            Predictor.put(233,0);
            Predictor.put(234,0);
            Predictor.put(235,0);
            Predictor.put(236,0);
            Predictor.put(237,0);
            Predictor.put(238,0);
            Predictor.put(239,0);
            Predictor.put(240,0);
            Predictor.put(241,0);
            Predictor.put(242,0);
            Predictor.put(243,0);
            Predictor.put(244,0);
            Predictor.put(245,0);
            Predictor.put(246,0);
            Predictor.put(247,0);
            Predictor.put(248,0);
            Predictor.put(249,0);
            Predictor.put(250,0);
            Predictor.put(251,0);
            Predictor.put(252,0);
            Predictor.put(253,0);
            Predictor.put(254,0);
            Predictor.put(255,0);
        }

    public Integer[] getGHR() {
        return GHR;
    }

    public Integer getTotalPredicted() {
        return totalPredicted;
    }

    public Integer getCorrectPredicted() {
        return correctPredicted;
    }

    public Map<Integer, Integer> getPredictor() {
        return Predictor;
    }

        private static void clearGHR(){
            for(int i = 0; i<GHR_SIZE; i++)
                GHR[i] = 0;
        }
        public static void setGHR(Integer _GHR_SIZE) //throws Exception
        {
       /*     if(GHR_SIZE != 8 || GHR_SIZE != 4 || GHR_SIZE != 2)
                throw new Exception();
*/
            GHR_SIZE = _GHR_SIZE;
            GHR = new Integer[_GHR_SIZE];
            clearGHR();
            totalPredicted=0;
            correctPredicted=0;
        }

        private  static void print_GHR()
        {
            System.out.print("[ ");
            for (int i = GHR_SIZE-1; i>= 0; i--) {
                System.out.print(" | " + GHR[i]);
            }
             System.out.println(" ]");
        }
        private static void shiftReg() {
            for (int i = GHR_SIZE - 1; i > 0; i--) {
                GHR[i] = GHR[i - 1];
            }
        }


        private static Integer hash(Integer GHR[])
        {
            Integer addr = 0;
             for (int i = 0; i< GHR.length; i++)
                 addr += GHR[i] * (int)Math.pow(2,i);
             return addr;
        }

    public static Integer getPrediction(Instruction ins){
        /*Step 1 - validity test*/
        Integer prediction = 0;
        /*step 2 - check if the instruction is a branch*/
        try {
            if (ins instanceof I_Instruction) {
                if (BinaryToMnemonicTable.getOpMap().get(ins.getOpCode()).equals("beq")){
                    totalPredicted++;
                    if(ALU.getResult(ins) == 1) {
                            shiftReg();
                            GHR[0] = 1;
                            prediction = 1;
                    }else{
                            shiftReg();
                            GHR[0] = 0;
                            prediction = 0;
                    }
                    return prediction;
                }else if (BinaryToMnemonicTable.getOpMap().get(ins.getOpCode()).equals("bne")) {
                    totalPredicted++;
                    if (ALU.getResult(ins) == 1) {
                        shiftReg();
                        GHR[0] = 1;
                        prediction = 1;
                    } else {
                        shiftReg();
                        GHR[0] = 0;
                        prediction = 0;
                    }
                    return prediction;
                }else {
                    return -1;
                }
            }else{
                return -1;
            }
        }catch (Exception e) {
            System.out.println(e);
        }
        return prediction;
    }
    /*checkPredictor: changes the prediction if needed for hashed address value at bp
            prediction - is the value of ALU.getResults(
     */
    public static void checkPredictor(Instruction ins)
    {
        final Integer SNT = 0;
        final Integer WNT = 1;
        final Integer WT = 2;
        final Integer ST = 3;

       /*Step 1 - get prediction*/
        Integer prediction;
        Integer addr = hash(GHR);
       /*getPrediction : will return if not a bne or branch instruction
       *                   else it will give us the prediction of the instruction
       *                    0 - if branch not taken
       *                    1 - if branch taken
       *                   */
       if((prediction=getPrediction((ins))) == -1) {
           return;
       }
        /*Step 2 - update predictor*/
        switch (Predictor.get(addr)){

            case 0 :  //Strongly not taken (00)
                if(prediction == 0){
                    Predictor.replace(addr, SNT);/*dont do anything**/
                    correctPredicted++;
                }
                else if(prediction == 1){
                   Predictor.replace(addr, WNT);
                }
                break;
            case 1: //Weakly not taken (01)
                if(prediction == 0){
                    Predictor.replace(addr, SNT);
                   correctPredicted++;
                }
                else if(prediction == 1){
                   Predictor.replace(addr, WT);
                }
                break;
            case 2: //Weakly taken (10)
                if(prediction == 0){
                    Predictor.replace(addr, WNT);
                }
                else if(prediction == 1){
                   Predictor.replace(addr, ST);
                   correctPredicted++;
                }
                break;
            case 3: //Strongly Taken
                if(prediction == 0){
                    Predictor.replace(addr, WT);
                }
                else if(prediction == 1){
                    /*essentially not doing anything*/
                   Predictor.replace(addr, ST);
                   correctPredicted++;
                }
                default:

        }
    }


    public static void printStats()
    {
        float avgPredicted;
        if(correctPredicted == 0 && totalPredicted == 0)
            System.out.println(100 + "% ( " + correctPredicted + " correct predictions, " + totalPredicted + " predictions )");
        else{
            avgPredicted  = ((float)correctPredicted)/totalPredicted*100;
            System.out.println(Math.round(avgPredicted*100.0)/100.0 + "% ( " + correctPredicted + " correct predictions, " + totalPredicted + " predictions )");
    }
    }
}