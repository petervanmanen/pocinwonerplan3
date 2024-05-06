import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISociaalteamdossiersoort } from 'app/shared/model/sociaalteamdossiersoort.model';
import { getEntity, updateEntity, createEntity, reset } from './sociaalteamdossiersoort.reducer';

export const SociaalteamdossiersoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sociaalteamdossiersoortEntity = useAppSelector(state => state.sociaalteamdossiersoort.entity);
  const loading = useAppSelector(state => state.sociaalteamdossiersoort.loading);
  const updating = useAppSelector(state => state.sociaalteamdossiersoort.updating);
  const updateSuccess = useAppSelector(state => state.sociaalteamdossiersoort.updateSuccess);

  const handleClose = () => {
    navigate('/sociaalteamdossiersoort');
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
      ...sociaalteamdossiersoortEntity,
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
          ...sociaalteamdossiersoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sociaalteamdossiersoort.home.createOrEditLabel" data-cy="SociaalteamdossiersoortCreateUpdateHeading">
            Create or edit a Sociaalteamdossiersoort
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
                <ValidatedField name="id" required readOnly id="sociaalteamdossiersoort-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="sociaalteamdossiersoort-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="sociaalteamdossiersoort-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sociaalteamdossiersoort" replace color="info">
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

export default SociaalteamdossiersoortUpdate;
