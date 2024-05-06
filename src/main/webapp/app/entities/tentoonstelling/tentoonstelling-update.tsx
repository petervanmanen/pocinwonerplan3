import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBruikleen } from 'app/shared/model/bruikleen.model';
import { getEntities as getBruikleens } from 'app/entities/bruikleen/bruikleen.reducer';
import { IMuseumobject } from 'app/shared/model/museumobject.model';
import { getEntities as getMuseumobjects } from 'app/entities/museumobject/museumobject.reducer';
import { ISamensteller } from 'app/shared/model/samensteller.model';
import { getEntities as getSamenstellers } from 'app/entities/samensteller/samensteller.reducer';
import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';
import { getEntity, updateEntity, createEntity, reset } from './tentoonstelling.reducer';

export const TentoonstellingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bruikleens = useAppSelector(state => state.bruikleen.entities);
  const museumobjects = useAppSelector(state => state.museumobject.entities);
  const samenstellers = useAppSelector(state => state.samensteller.entities);
  const tentoonstellingEntity = useAppSelector(state => state.tentoonstelling.entity);
  const loading = useAppSelector(state => state.tentoonstelling.loading);
  const updating = useAppSelector(state => state.tentoonstelling.updating);
  const updateSuccess = useAppSelector(state => state.tentoonstelling.updateSuccess);

  const handleClose = () => {
    navigate('/tentoonstelling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBruikleens({}));
    dispatch(getMuseumobjects({}));
    dispatch(getSamenstellers({}));
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
      ...tentoonstellingEntity,
      ...values,
      isbedoeldvoorBruikleens: mapIdList(values.isbedoeldvoorBruikleens),
      onderdeelMuseumobjects: mapIdList(values.onderdeelMuseumobjects),
      steltsamenSamenstellers: mapIdList(values.steltsamenSamenstellers),
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
          ...tentoonstellingEntity,
          isbedoeldvoorBruikleens: tentoonstellingEntity?.isbedoeldvoorBruikleens?.map(e => e.id.toString()),
          onderdeelMuseumobjects: tentoonstellingEntity?.onderdeelMuseumobjects?.map(e => e.id.toString()),
          steltsamenSamenstellers: tentoonstellingEntity?.steltsamenSamenstellers?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.tentoonstelling.home.createOrEditLabel" data-cy="TentoonstellingCreateUpdateHeading">
            Create or edit a Tentoonstelling
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
                <ValidatedField name="id" required readOnly id="tentoonstelling-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="tentoonstelling-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="tentoonstelling-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Omschrijving"
                id="tentoonstelling-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Subtitel" id="tentoonstelling-subtitel" name="subtitel" data-cy="subtitel" type="text" />
              <ValidatedField label="Titel" id="tentoonstelling-titel" name="titel" data-cy="titel" type="text" />
              <ValidatedField
                label="Isbedoeldvoor Bruikleen"
                id="tentoonstelling-isbedoeldvoorBruikleen"
                data-cy="isbedoeldvoorBruikleen"
                type="select"
                multiple
                name="isbedoeldvoorBruikleens"
              >
                <option value="" key="0" />
                {bruikleens
                  ? bruikleens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Onderdeel Museumobject"
                id="tentoonstelling-onderdeelMuseumobject"
                data-cy="onderdeelMuseumobject"
                type="select"
                multiple
                name="onderdeelMuseumobjects"
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
              <ValidatedField
                label="Steltsamen Samensteller"
                id="tentoonstelling-steltsamenSamensteller"
                data-cy="steltsamenSamensteller"
                type="select"
                multiple
                name="steltsamenSamenstellers"
              >
                <option value="" key="0" />
                {samenstellers
                  ? samenstellers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tentoonstelling" replace color="info">
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

export default TentoonstellingUpdate;
