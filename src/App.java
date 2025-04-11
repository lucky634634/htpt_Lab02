public class App {
    public static void main(String[] args) throws Exception {
        // GameFrame frame = new GameFrame(true);
        // frame.run();
        if (args.length == 2) {
            Server.GetInstance().Start(ConvertStringToInt(args[1]));
            ScoreManager.GetInstance().CreateNewPlayer(args[0]);
            Thread t = new Thread(new GameFrame(true));
            t.start();
        } else if (args.length == 3) {
            String serverAddr = args[1];
            int serverPort = ConvertStringToInt(args[2]);
            String name = args[0];
            System.out.println("Connecting to server at: " + serverAddr + ":" + serverPort + " with name: " + name);

            Thread t = new Thread(new GameFrame(false));
            t.start();

            Client.GetInstance().Start(serverAddr, serverPort);
            Client.GetInstance().SendMessage(new RequestId(name));
        } else {
            new SelectForm();
        }
    }

    private static boolean CheckNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int ConvertStringToInt(String str) {
        if (CheckNumber(str)) {
            return Integer.parseInt(str);
        } else {
            throw new IllegalArgumentException("Invalid input: " + str);
        }
    }
}
