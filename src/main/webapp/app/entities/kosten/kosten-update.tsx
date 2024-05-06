import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKosten } from 'app/shared/model/kosten.model';
import { getEntity, updateEntity, createEntity, reset } from './kosten.reducer';

export const KostenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kostenEntity = useAppSelector(state => state.kosten.entity);
  const loading = useAppSelector(state => state.kosten.loading);
  const updating = useAppSelector(state => state.kosten.updating);
  const updateSuccess = useAppSelector(state => state.kosten.updateSuccess);

  const handleClose = () => {
    navigate('/kosten');
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
      ...kostenEntity,
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
          ...kostenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kosten.home.createOrEditLabel" data-cy="KostenCreateUpdateHeading">
            Create or edit a Kosten
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kosten-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aangemaaktdoor"
                id="kosten-aangemaaktdoor"
                name="aangemaaktdoor"
                data-cy="aangemaaktdoor"
                type="text"
              />
              <ValidatedField
                label="Aantal"
                id="kosten-aantal"
                name="aantal"
                data-cy="aantal"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Bedrag" id="kosten-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Bedragtotaal" id="kosten-bedragtotaal" name="bedragtotaal" data-cy="bedragtotaal" type="text" />
              <ValidatedField label="Datumaanmaak" id="kosten-datumaanmaak" name="datumaanmaak" data-cy="datumaanmaak" type="date" />
              <ValidatedField label="Datummutatie" id="kosten-datummutatie" name="datummutatie" data-cy="datummutatie" type="date" />
              <ValidatedField
                label="Eenheid"
                id="kosten-eenheid"
                name="eenheid"
                data-cy="eenheid"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Geaccordeerd"
                id="kosten-geaccordeerd"
                name="geaccordeerd"
                data-cy="geaccordeerd"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Gefactureerdop"
                id="kosten-gefactureerdop"
                name="gefactureerdop"
                data-cy="gefactureerdop"
                type="date"
              />
              <ValidatedField label="Gemuteerddoor" id="kosten-gemuteerddoor" name="gemuteerddoor" data-cy="gemuteerddoor" type="text" />
              <ValidatedField
                label="Naam"
                id="kosten-naam"
                name="naam"
                data-cy="naam"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="kosten-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Opbasisvangrondslag"
                id="kosten-opbasisvangrondslag"
                name="opbasisvangrondslag"
                data-cy="opbasisvangrondslag"
                type="text"
              />
              <ValidatedField label="Tarief" id="kosten-tarief" name="tarief" data-cy="tarief" type="text" />
              <ValidatedField label="Type" id="kosten-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Vastgesteldbedrag"
                id="kosten-vastgesteldbedrag"
                name="vastgesteldbedrag"
                data-cy="vastgesteldbedrag"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kosten" replace color="info">
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

export default KostenUpdate;
