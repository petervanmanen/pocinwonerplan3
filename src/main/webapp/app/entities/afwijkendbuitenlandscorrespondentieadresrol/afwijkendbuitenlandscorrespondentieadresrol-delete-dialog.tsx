import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './afwijkendbuitenlandscorrespondentieadresrol.reducer';

export const AfwijkendbuitenlandscorrespondentieadresrolDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const afwijkendbuitenlandscorrespondentieadresrolEntity = useAppSelector(
    state => state.afwijkendbuitenlandscorrespondentieadresrol.entity,
  );
  const updateSuccess = useAppSelector(state => state.afwijkendbuitenlandscorrespondentieadresrol.updateSuccess);

  const handleClose = () => {
    navigate('/afwijkendbuitenlandscorrespondentieadresrol');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(afwijkendbuitenlandscorrespondentieadresrolEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="afwijkendbuitenlandscorrespondentieadresrolDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.afwijkendbuitenlandscorrespondentieadresrol.delete.question">
        Are you sure you want to delete Afwijkendbuitenlandscorrespondentieadresrol {afwijkendbuitenlandscorrespondentieadresrolEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-afwijkendbuitenlandscorrespondentieadresrol"
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

export default AfwijkendbuitenlandscorrespondentieadresrolDeleteDialog;
