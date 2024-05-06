import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOpenbareactiviteit } from 'app/shared/model/openbareactiviteit.model';
import { getEntity, updateEntity, createEntity, reset } from './openbareactiviteit.reducer';

export const OpenbareactiviteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const openbareactiviteitEntity = useAppSelector(state => state.openbareactiviteit.entity);
  const loading = useAppSelector(state => state.openbareactiviteit.loading);
  const updating = useAppSelector(state => state.openbareactiviteit.updating);
  const updateSuccess = useAppSelector(state => state.openbareactiviteit.updateSuccess);

  const handleClose = () => {
    navigate('/openbareactiviteit');
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
      ...openbareactiviteitEntity,
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
          ...openbareactiviteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.openbareactiviteit.home.createOrEditLabel" data-cy="OpenbareactiviteitCreateUpdateHeading">
            Create or edit a Openbareactiviteit
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
                <ValidatedField name="id" required readOnly id="openbareactiviteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="openbareactiviteit-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="openbareactiviteit-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Evenmentnaam"
                id="openbareactiviteit-evenmentnaam"
                name="evenmentnaam"
                data-cy="evenmentnaam"
                type="text"
              />
              <ValidatedField
                label="Locatieomschrijving"
                id="openbareactiviteit-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
              />
              <ValidatedField label="Status" id="openbareactiviteit-status" name="status" data-cy="status" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/openbareactiviteit" replace color="info">
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

export default OpenbareactiviteitUpdate;
