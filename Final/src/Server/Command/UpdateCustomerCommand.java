package Server.Command;

import Model.Customer;
import Server.Command.RemoteCommand;
import Server.RemoteModelManager;
import Shared.RemoteModel;

import java.io.IOException;

public class UpdateCustomerCommand implements RemoteCommand<Void> {

    private final Customer oldC;
    private final Customer editedC;

    public UpdateCustomerCommand(Customer oldC, Customer editedC) {
        this.oldC = oldC;
        this.editedC = editedC;
    }



    @Override
    public Void execute(RemoteModel model) throws IOException {
        model.updateCustomer(oldC, editedC);
        return null;
    }
}

