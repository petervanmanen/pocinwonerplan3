import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanvraagvrijstelling } from 'app/shared/model/aanvraagvrijstelling.model';
import { getEntity, updateEntity, createEntity, reset } from './aanvraagvrijstelling.reducer';

export const AanvraagvrijstellingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanvraagvrijstellingEntity = useAppSelector(state => state.aanvraagvrijstelling.entity);
  const loading = useAppSelector(state => state.aanvraagvrijstelling.loading);
  const updating = useAppSelector(state => state.aanvraagvrijstelling.updating);
  const updateSuccess = useAppSelector(state => state.aanvraagvrijstelling.updateSuccess);

  const handleClose = () => {
    navigate('/aanvraagvrijstelling');
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
      ...aanvraagvrijstellingEntity,
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
          ...aanvraagvrijstellingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanvraagvrijstelling.home.createOrEditLabel" data-cy="AanvraagvrijstellingCreateUpdateHeading">
            Create or edit a Aanvraagvrijstelling
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
                <ValidatedField name="id" required readOnly id="aanvraagvrijstelling-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Buitenlandseschoollocatie"
                id="aanvraagvrijstelling-buitenlandseschoollocatie"
                name="buitenlandseschoollocatie"
                data-cy="buitenlandseschoollocatie"
                type="text"
              />
              <ValidatedField
                label="Datumaanvraag"
                id="aanvraagvrijstelling-datumaanvraag"
                name="datumaanvraag"
                data-cy="datumaanvraag"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanvraagvrijstelling" replace color="info">
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

export default AanvraagvrijstellingUpdate;
