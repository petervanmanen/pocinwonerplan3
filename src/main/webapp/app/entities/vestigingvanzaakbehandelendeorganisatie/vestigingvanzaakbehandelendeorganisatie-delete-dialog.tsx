import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './vestigingvanzaakbehandelendeorganisatie.reducer';

export const VestigingvanzaakbehandelendeorganisatieDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const vestigingvanzaakbehandelendeorganisatieEntity = useAppSelector(state => state.vestigingvanzaakbehandelendeorganisatie.entity);
  const updateSuccess = useAppSelector(state => state.vestigingvanzaakbehandelendeorganisatie.updateSuccess);

  const handleClose = () => {
    navigate('/vestigingvanzaakbehandelendeorganisatie');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(vestigingvanzaakbehandelendeorganisatieEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="vestigingvanzaakbehandelendeorganisatieDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.vestigingvanzaakbehandelendeorganisatie.delete.question">
        Are you sure you want to delete Vestigingvanzaakbehandelendeorganisatie {vestigingvanzaakbehandelendeorganisatieEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-vestigingvanzaakbehandelendeorganisatie"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default VestigingvanzaakbehandelendeorganisatieDeleteDialog;
