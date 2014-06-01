package hogwarts.example;
import hogwarts.example.MathData;
import java.util.List;

interface MathQuestion{
	void answer(in List<MathData> mathData);
	List<MathData> getQuestions();
	
}
