import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMuseumobject } from 'app/shared/model/museumobject.model';
import { getEntities as getMuseumobjects } from 'app/entities/museumobject/museumobject.reducer';
import { IIncident } from 'app/shared/model/incident.model';
import { getEntity, updateEntity, createEntity, reset } from './incident.reducer';

export const IncidentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const museumobjects = useAppSelector(state => state.museumobject.entities);
  const incidentEntity = useAppSelector(state => state.incident.entity);
  const loading = useAppSelector(state => state.incident.loading);
  const updating = useAppSelector(state => state.incident.updating);
  const updateSuccess = useAppSelector(state => state.incident.updateSuccess);

  const handleClose = () => {
    navigate('/incident');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMuseumobjects({}));
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
      ...incidentEntity,
      ...values,
      betreftMuseumobjects: mapIdList(values.betreftMuseumobjects),
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
          ...incidentEntity,
          betreftMuseumobjects: incidentEntity?.betreftMuseumobjects?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.incident.home.createOrEditLabel" data-cy="IncidentCreateUpdateHeading">
            Create or edit a Incident
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="incident-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="incident-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Locatie" id="incident-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Naam" id="incident-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="incident-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Betreft Museumobject"
                id="incident-betreftMuseumobject"
                data-cy="betreftMuseumobject"
                type="select"
                multiple
                name="betreftMuseumobjects"
              >
                <option value="" key="0" />
                {museumobjects
                  ? museumobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/incident" replace color="info">
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

export default IncidentUpdate;
