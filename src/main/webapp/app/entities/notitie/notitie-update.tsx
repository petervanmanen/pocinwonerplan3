import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IApplicatie } from 'app/shared/model/applicatie.model';
import { getEntities as getApplicaties } from 'app/entities/applicatie/applicatie.reducer';
import { INotitie } from 'app/shared/model/notitie.model';
import { getEntity, updateEntity, createEntity, reset } from './notitie.reducer';

export const NotitieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const applicaties = useAppSelector(state => state.applicatie.entities);
  const notitieEntity = useAppSelector(state => state.notitie.entity);
  const loading = useAppSelector(state => state.notitie.loading);
  const updating = useAppSelector(state => state.notitie.updating);
  const updateSuccess = useAppSelector(state => state.notitie.updateSuccess);

  const handleClose = () => {
    navigate('/notitie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedewerkers({}));
    dispatch(getApplicaties({}));
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
      ...notitieEntity,
      ...values,
      auteurMedewerker: medewerkers.find(it => it.id.toString() === values.auteurMedewerker?.toString()),
      heeftnotitiesApplicatie: applicaties.find(it => it.id.toString() === values.heeftnotitiesApplicatie?.toString()),
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
          ...notitieEntity,
          auteurMedewerker: notitieEntity?.auteurMedewerker?.id,
          heeftnotitiesApplicatie: notitieEntity?.heeftnotitiesApplicatie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.notitie.home.createOrEditLabel" data-cy="NotitieCreateUpdateHeading">
            Create or edit a Notitie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="notitie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="notitie-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Inhoud" id="notitie-inhoud" name="inhoud" data-cy="inhoud" type="text" />
              <ValidatedField
                id="notitie-auteurMedewerker"
                name="auteurMedewerker"
                data-cy="auteurMedewerker"
                label="Auteur Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="notitie-heeftnotitiesApplicatie"
                name="heeftnotitiesApplicatie"
                data-cy="heeftnotitiesApplicatie"
                label="Heeftnotities Applicatie"
                type="select"
              >
                <option value="" key="0" />
                {applicaties
                  ? applicaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notitie" replace color="info">
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

export default NotitieUpdate;
