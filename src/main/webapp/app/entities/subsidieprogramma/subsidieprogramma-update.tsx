import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubsidieprogramma } from 'app/shared/model/subsidieprogramma.model';
import { getEntity, updateEntity, createEntity, reset } from './subsidieprogramma.reducer';

export const SubsidieprogrammaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const subsidieprogrammaEntity = useAppSelector(state => state.subsidieprogramma.entity);
  const loading = useAppSelector(state => state.subsidieprogramma.loading);
  const updating = useAppSelector(state => state.subsidieprogramma.updating);
  const updateSuccess = useAppSelector(state => state.subsidieprogramma.updateSuccess);

  const handleClose = () => {
    navigate('/subsidieprogramma');
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
    if (values.programmabegroting !== undefined && typeof values.programmabegroting !== 'number') {
      values.programmabegroting = Number(values.programmabegroting);
    }

    const entity = {
      ...subsidieprogrammaEntity,
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
          ...subsidieprogrammaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.subsidieprogramma.home.createOrEditLabel" data-cy="SubsidieprogrammaCreateUpdateHeading">
            Create or edit a Subsidieprogramma
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
                <ValidatedField name="id" required readOnly id="subsidieprogramma-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="subsidieprogramma-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="subsidieprogramma-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Naam" id="subsidieprogramma-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="subsidieprogramma-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Programmabegroting"
                id="subsidieprogramma-programmabegroting"
                name="programmabegroting"
                data-cy="programmabegroting"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subsidieprogramma" replace color="info">
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

export default SubsidieprogrammaUpdate;
