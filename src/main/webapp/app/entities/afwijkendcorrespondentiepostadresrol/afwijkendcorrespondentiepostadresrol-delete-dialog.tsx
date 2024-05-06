import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './afwijkendcorrespondentiepostadresrol.reducer';

export const AfwijkendcorrespondentiepostadresrolDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const afwijkendcorrespondentiepostadresrolEntity = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.entity);
  const updateSuccess = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.updateSuccess);

  const handleClose = () => {
    navigate('/afwijkendcorrespondentiepostadresrol');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(afwijkendcorrespondentiepostadresrolEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="afwijkendcorrespondentiepostadresrolDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.afwijkendcorrespondentiepostadresrol.delete.question">
        Are you sure you want to delete Afwijkendcorrespondentiepostadresrol {afwijkendcorrespondentiepostadresrolEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-afwijkendcorrespondentiepostadresrol"
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

export default AfwijkendcorrespondentiepostadresrolDeleteDialog;
