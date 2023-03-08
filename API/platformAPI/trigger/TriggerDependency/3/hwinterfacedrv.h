
#define IOCTL_READ_PORT_UCHAR	 -1673519100 //CTL_CODE(40000, 0x801, METHOD_BUFFERED, FILE_ANY_ACCESS)
#define IOCTL_WRITE_PORT_UCHAR	 -1673519096 //CTL_CODE(40000, 0x802, METHOD_BUFFERED, FILE_ANY_ACCESS)
#define IOCTL_READ_PORT_USHORT	 -1673519092 //CTL_CODE(40000, 0x803, METHOD_BUFFERED, FILE_ANY_ACCESS)
#define IOCTL_WRITE_PORT_USHORT	 -1673519088 //CTL_CODE(40000, 0x804, METHOD_BUFFERED, FILE_ANY_ACCESS)
#define IOCTL_READ_PORT_ULONG	 -1673519084 //CTL_CODE(40000, 0x805, METHOD_BUFFERED, FILE_ANY_ACCESS)
#define IOCTL_WRITE_PORT_ULONG	 -1673519080 //CTL_CODE(40000, 0x806, METHOD_BUFFERED, FILE_ANY_ACCESS)

#define IOCTL_WINIO_MAPPHYSTOLIN  -1673519076
#define IOCTL_WINIO_UNMAPPHYSADDR -1673519072

#pragma pack(push)
#pragma pack(1)

struct tagPhys32Struct
{
  HANDLE PhysicalMemoryHandle;
  SIZE_T dwPhysMemSizeInBytes;
  PVOID pvPhysAddress;
  PVOID pvPhysMemLin;
};

extern struct tagPhys32Struct Phys32Struct;

#pragma pack(pop)