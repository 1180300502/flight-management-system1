package P1;

import java.util.List;


public interface PlanningEntry<L> {
	public void Run();//�����ƻ���
	public void Cancel();//ȡ���ƻ���
	public void Block();//����ƻ���
	public void Commplete();//��ɼƻ���
	public String getname();//��ȡ�ƻ��������
	public String getstate();//��ȡ��ǰ״̬
	public List<Location> getlocations();//��ȡλ��
	public List<L> getresources();//��ȡ��Դ
	public List<TimesLot> gettimeslot();//��ȡʱ���
}

