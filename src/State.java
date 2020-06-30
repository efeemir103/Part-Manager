public class State {
    private static Project selectedProject = null;
    private static Part selectedPart = null;
    private static Material selectedMaterial = null;

    public static Project getSelectedProject() {
        return selectedProject;
    }

    public static void setSelectedProject(Project selectedProject) {
        State.selectedProject = selectedProject;
    }

    public static Part getSelectedPart() {
        return selectedPart;
    }

    public static void setSelectedPart(Part selectedPart) {
        State.selectedPart = selectedPart;
    }

    public static Material getSelectedMaterial() {
        return selectedMaterial;
    }

    public static void setSelectedMaterial(Material selectedMaterial) {
        State.selectedMaterial = selectedMaterial;
    }
    
}