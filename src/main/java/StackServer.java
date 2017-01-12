import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

@Path("/stack")
public class StackServer {

    private static Map<UUID, Stack<Integer>> stacks = new HashMap<>();
    private static Stack<Integer> GetStack(UUID id) {
        Stack<Integer> stack;
        if (!stacks.containsKey(id)) {
            stack = new Stack<Integer>();
            stacks.put(id, stack);
        }
        else {
            stack = stacks.get(id);
        }
        return stack;
    }

    @GET
    @Path("/{id}/push/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Push(@PathParam("id") UUID id, @PathParam("value") int value) {
        Stack<Integer> stack = GetStack(id);
        stack.push(value);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/pop")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Pop(@PathParam("id") UUID id) {
        Stack<Integer> stack = GetStack(id);
        if (stack.isEmpty())
            return Response.noContent().build();
        int value = stack.pop();
        return Response.ok(value).build();
    }

    @GET
    @Path("/{id}/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response List(@PathParam("id") UUID id) {
        Stack<Integer> stack = GetStack(id);
        Integer[] values = stack.toArray(new Integer[stack.size()]);
        GenericEntity<Integer[]> list = new GenericEntity<Integer[]>(values) { };
        return Response.ok(list).build();
    }

}
