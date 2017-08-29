package sccc.eample.mycarer_stroke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StrokeInfoFragment extends Fragment implements View.OnClickListener {

    View view;
    FragmentTransaction fragmentTransaction;
    TextView tv_strokeinfo;

    public StrokeInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stroke_info, container, false);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Stroke Information");
        tv_strokeinfo = (TextView) view.findViewById(R.id.stroke_info_tv);
        tv_strokeinfo.setText("Different types of stroke:\n" +
                "\n" +
                "Most Strokes are caused by blockage cutting off the blood supply to the brain. This is known as Ischaemic Stroke.\n" +
                "\n" +
                "Stroke is when poor blood flow to the brain results in cell death. There are two main types of stroke: ischemic, due to lack of blood flow, and hemorrhagic, due to bleeding. They result in part of the brain not functioning properly. Signs and symptoms of a stroke may include an inability to move or feel on one side of the body, problems understanding or speaking, feeling like the world is spinning, or loss of vision to one side. Signs and symptoms often appear soon after the stroke has occurred. If symptoms last less than one or two hours it is known as a transient ischemic attack (TIA) or mini-stroke.[ A hemorrhagic stroke may also be associated with a severe headache. The symptoms of a stroke can be permanent. Long-term complications may include pneumonia or loss of bladder control.\n" +
                "\n" +
                "The main risk factor for stroke is high blood pressure.Other risk factors include tobacco smoking, obesity, high blood cholesterol, diabetes mellitus, previous TIA, and atrial fibrillation.[2][4] An ischemic stroke is typically caused by blockage of a blood vessel, though there are also less common causes. A hemorrhagic stroke is caused by either bleeding directly into the brain or into the space between the brain's membranes. Bleeding may occur due to a ruptured brain aneurysm. Diagnosis is typically with medical imaging such as a CT scan or magnetic resonance imaging (MRI) scan along with a physical exam. Other tests such as an electrocardiogram (ECG) and blood tests are done to determine risk factors and rule out other possible causes. Low blood sugar may cause similar symptoms.\n" +
                "\n" +
                "However, strokes can also be caused by a bleeding in or around the brain. This is a haemorrhagic stroke.\n" +
                "\n" +
                "Subarachnoid hemorrhage (SAH) is bleeding into the subarachnoid space — the area between the arachnoid membrane and the pia mater surrounding the brain. Symptoms of SAH include a severe headache with a rapid onset (\"thunderclap headache\"), vomiting, confusion or a lowered level of consciousness, and sometimes seizures. Neck stiffness or neck pain are also relatively common.\n" +
                "\n" +
                "SAH may occur spontaneously, usually from a ruptured cerebral aneurysm, or may result from head injury. In general, the diagnosis can be determined by a CT scan of the head if done within six hours. Occasionally a lumbar puncture is also required. After confirmation of bleeding further tests are usually performed to find problems that may have caused it, such as an aneurysm.\n" +
                "\n" +
                "\n" +
                "\n" +
                "Aphasia:\n" +
                "\n" +
                "Aphasia is an inability to comprehend and formulate language because of damage to specific brain regions. This damage is typically caused by a cerebral vascular accident (stroke), or head trauma, however these are not the only possible causes. To be diagnosed with aphasia, a person's speech or language must be significantly impaired in one (or several) of the four communication modalities following acquired brain injury or have significant decline over a short time period (progressive aphasia). The four communication modalities are auditory comprehension, verbal expression, reading and writing, and functional communication.\n" +
                "\n" +
                "The difficulties of people with aphasia can range from occasional trouble finding words to losing the ability to speak, read, or write; intelligence, however, is unaffected. Expressive language and receptive language can both be affected as well. Aphasia also affects visual language such as sign language. In contrast, the use of formulaic expressions in everyday communication is often preserved. One prevalent deficit in the aphasias is anomia, which is a deficit in word finding ability.\n" +
                "\n" +
                "Causes of stroke:\n" +
                "\n" +
                "•\thigh blood pressure (hypertension)\n" +
                "•\thigh cholesterol\n" +
                "•\tatrial fibrillation\n" +
                "•\tdiabetes\n" +
                "•\tage\n" +
                "\n" +
                "Preventing a stroke:\n" +
                "\n" +
                "You can significantly reduce your risk of having a stroke through a healthy lifestyle, such as:\n" +
                "•\teating a healthy diet\n" +
                "•\ttaking regular exercise\n" +
                "•\tdrinking alcohol in moderation\n" +
                "•\tnot smoking\n" +
                "\n" +
                "\n");

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
