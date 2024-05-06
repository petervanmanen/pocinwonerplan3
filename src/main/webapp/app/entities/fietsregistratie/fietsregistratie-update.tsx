import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFietsregistratie } from 'app/shared/model/fietsregistratie.model';
import { getEntity, updateEntity, createEntity, reset } from './fietsregistratie.reducer';

export const FietsregistratieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fietsregistratieEntity = useAppSelector(state => state.fietsregistratie.entity);
  const loading = useAppSelector(state => state.fietsregistratie.loading);
  const updating = useAppSelector(state => state.fietsregistratie.updating);
  const updateSuccess = useAppSelector(state => state.fietsregistratie.updateSuccess);

  const handleClose = () => {
    navigate('/fietsregistratie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...fietsregistratieEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...fietsregistratieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.fietsregistratie.home.createOrEditLabel" data-cy="FietsregistratieCreateUpdateHeading">
            Create or edit a Fietsregistratie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="fietsregistratie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Gelabeld" id="fietsregistratie-gelabeld" name="gelabeld" data-cy="gelabeld" type="text" />
              <ValidatedField label="Verwijderd" id="fietsregistratie-verwijderd" name="verwijderd" data-cy="verwijderd" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fietsregistratie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FietsregistratieUpdate;
