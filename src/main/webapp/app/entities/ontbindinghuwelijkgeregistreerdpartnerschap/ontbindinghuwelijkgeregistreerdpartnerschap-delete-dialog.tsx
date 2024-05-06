import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './ontbindinghuwelijkgeregistreerdpartnerschap.reducer';

export const OntbindinghuwelijkgeregistreerdpartnerschapDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const ontbindinghuwelijkgeregistreerdpartnerschapEntity = useAppSelector(
    state => state.ontbindinghuwelijkgeregistreerdpartnerschap.entity,
  );
  const updateSuccess = useAppSelector(state => state.ontbindinghuwelijkgeregistreerdpartnerschap.updateSuccess);

  const handleClose = () => {
    navigate('/ontbindinghuwelijkgeregistreerdpartnerschap');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(ontbindinghuwelijkgeregistreerdpartnerschapEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="ontbindinghuwelijkgeregistreerdpartnerschapDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.ontbindinghuwelijkgeregistreerdpartnerschap.delete.question">
        Are you sure you want to delete Ontbindinghuwelijkgeregistreerdpartnerschap {ontbindinghuwelijkgeregistreerdpartnerschapEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-ontbindinghuwelijkgeregistreerdpartnerschap"
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

export default OntbindinghuwelijkgeregistreerdpartnerschapDeleteDialog;
