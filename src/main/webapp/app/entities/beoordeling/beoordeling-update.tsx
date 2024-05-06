import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntities as getWerknemers } from 'app/entities/werknemer/werknemer.reducer';
import { IBeoordeling } from 'app/shared/model/beoordeling.model';
import { getEntity, updateEntity, createEntity, reset } from './beoordeling.reducer';

export const BeoordelingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const werknemers = useAppSelector(state => state.werknemer.entities);
  const beoordelingEntity = useAppSelector(state => state.beoordeling.entity);
  const loading = useAppSelector(state => state.beoordeling.loading);
  const updating = useAppSelector(state => state.beoordeling.updating);
  const updateSuccess = useAppSelector(state => state.beoordeling.updateSuccess);

  const handleClose = () => {
    navigate('/beoordeling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWerknemers({}));
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
      ...beoordelingEntity,
      ...values,
      beoordeeltdoorWerknemer: werknemers.find(it => it.id.toString() === values.beoordeeltdoorWerknemer?.toString()),
      beoordelingvanWerknemer: werknemers.find(it => it.id.toString() === values.beoordelingvanWerknemer?.toString()),
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
          ...beoordelingEntity,
          beoordeeltdoorWerknemer: beoordelingEntity?.beoordeeltdoorWerknemer?.id,
          beoordelingvanWerknemer: beoordelingEntity?.beoordelingvanWerknemer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beoordeling.home.createOrEditLabel" data-cy="BeoordelingCreateUpdateHeading">
            Create or edit a Beoordeling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="beoordeling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="beoordeling-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Omschrijving" id="beoordeling-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Oordeel" id="beoordeling-oordeel" name="oordeel" data-cy="oordeel" type="text" />
              <ValidatedField
                id="beoordeling-beoordeeltdoorWerknemer"
                name="beoordeeltdoorWerknemer"
                data-cy="beoordeeltdoorWerknemer"
                label="Beoordeeltdoor Werknemer"
                type="select"
              >
                <option value="" key="0" />
                {werknemers
                  ? werknemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="beoordeling-beoordelingvanWerknemer"
                name="beoordelingvanWerknemer"
                data-cy="beoordelingvanWerknemer"
                label="Beoordelingvan Werknemer"
                type="select"
              >
                <option value="" key="0" />
                {werknemers
                  ? werknemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beoordeling" replace color="info">
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

export default BeoordelingUpdate;
