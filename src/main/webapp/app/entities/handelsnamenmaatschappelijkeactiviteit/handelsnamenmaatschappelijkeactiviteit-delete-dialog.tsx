import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './handelsnamenmaatschappelijkeactiviteit.reducer';

export const HandelsnamenmaatschappelijkeactiviteitDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const handelsnamenmaatschappelijkeactiviteitEntity = useAppSelector(state => state.handelsnamenmaatschappelijkeactiviteit.entity);
  const updateSuccess = useAppSelector(state => state.handelsnamenmaatschappelijkeactiviteit.updateSuccess);

  const handleClose = () => {
    navigate('/handelsnamenmaatschappelijkeactiviteit');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(handelsnamenmaatschappelijkeactiviteitEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="handelsnamenmaatschappelijkeactiviteitDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="demo3App.handelsnamenmaatschappelijkeactiviteit.delete.question">
        Are you sure you want to delete Handelsnamenmaatschappelijkeactiviteit {handelsnamenmaatschappelijkeactiviteitEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button
          id="jhi-confirm-delete-handelsnamenmaatschappelijkeactiviteit"
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

export default HandelsnamenmaatschappelijkeactiviteitDeleteDialog;
