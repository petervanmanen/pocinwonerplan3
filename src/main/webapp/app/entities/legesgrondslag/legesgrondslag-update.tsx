import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILegesgrondslag } from 'app/shared/model/legesgrondslag.model';
import { getEntity, updateEntity, createEntity, reset } from './legesgrondslag.reducer';

export const LegesgrondslagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const legesgrondslagEntity = useAppSelector(state => state.legesgrondslag.entity);
  const loading = useAppSelector(state => state.legesgrondslag.loading);
  const updating = useAppSelector(state => state.legesgrondslag.updating);
  const updateSuccess = useAppSelector(state => state.legesgrondslag.updateSuccess);

  const handleClose = () => {
    navigate('/legesgrondslag');
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
      ...legesgrondslagEntity,
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
          ...legesgrondslagEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.legesgrondslag.home.createOrEditLabel" data-cy="LegesgrondslagCreateUpdateHeading">
            Create or edit a Legesgrondslag
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
                <ValidatedField name="id" required readOnly id="legesgrondslag-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aangemaaktdoor"
                id="legesgrondslag-aangemaaktdoor"
                name="aangemaaktdoor"
                data-cy="aangemaaktdoor"
                type="text"
              />
              <ValidatedField
                label="Aantalopgegeven"
                id="legesgrondslag-aantalopgegeven"
                name="aantalopgegeven"
                data-cy="aantalopgegeven"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Aantalvastgesteld"
                id="legesgrondslag-aantalvastgesteld"
                name="aantalvastgesteld"
                data-cy="aantalvastgesteld"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Automatisch"
                id="legesgrondslag-automatisch"
                name="automatisch"
                data-cy="automatisch"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Datumaanmaak"
                id="legesgrondslag-datumaanmaak"
                name="datumaanmaak"
                data-cy="datumaanmaak"
                type="text"
              />
              <ValidatedField
                label="Datummutatie"
                id="legesgrondslag-datummutatie"
                name="datummutatie"
                data-cy="datummutatie"
                type="date"
              />
              <ValidatedField
                label="Eenheid"
                id="legesgrondslag-eenheid"
                name="eenheid"
                data-cy="eenheid"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Gemuteerddoor"
                id="legesgrondslag-gemuteerddoor"
                name="gemuteerddoor"
                data-cy="gemuteerddoor"
                type="text"
              />
              <ValidatedField
                label="Legesgrondslag"
                id="legesgrondslag-legesgrondslag"
                name="legesgrondslag"
                data-cy="legesgrondslag"
                type="text"
              />
              <ValidatedField
                label="Omschrijving"
                id="legesgrondslag-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/legesgrondslag" replace color="info">
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

export default LegesgrondslagUpdate;
