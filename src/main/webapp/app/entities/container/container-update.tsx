import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFractie } from 'app/shared/model/fractie.model';
import { getEntities as getFracties } from 'app/entities/fractie/fractie.reducer';
import { IContainertype } from 'app/shared/model/containertype.model';
import { getEntities as getContainertypes } from 'app/entities/containertype/containertype.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IContainer } from 'app/shared/model/container.model';
import { getEntity, updateEntity, createEntity, reset } from './container.reducer';

export const ContainerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fracties = useAppSelector(state => state.fractie.entities);
  const containertypes = useAppSelector(state => state.containertype.entities);
  const locaties = useAppSelector(state => state.locatie.entities);
  const containerEntity = useAppSelector(state => state.container.entity);
  const loading = useAppSelector(state => state.container.loading);
  const updating = useAppSelector(state => state.container.updating);
  const updateSuccess = useAppSelector(state => state.container.updateSuccess);

  const handleClose = () => {
    navigate('/container');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFracties({}));
    dispatch(getContainertypes({}));
    dispatch(getLocaties({}));
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
      ...containerEntity,
      ...values,
      geschiktvoorFractie: fracties.find(it => it.id.toString() === values.geschiktvoorFractie?.toString()),
      soortContainertype: containertypes.find(it => it.id.toString() === values.soortContainertype?.toString()),
      heeftLocatie: locaties.find(it => it.id.toString() === values.heeftLocatie?.toString()),
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
          ...containerEntity,
          geschiktvoorFractie: containerEntity?.geschiktvoorFractie?.id,
          soortContainertype: containerEntity?.soortContainertype?.id,
          heeftLocatie: containerEntity?.heeftLocatie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.container.home.createOrEditLabel" data-cy="ContainerCreateUpdateHeading">
            Create or edit a Container
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="container-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Containercode" id="container-containercode" name="containercode" data-cy="containercode" type="text" />
              <ValidatedField label="Sensorid" id="container-sensorid" name="sensorid" data-cy="sensorid" type="text" />
              <ValidatedField
                id="container-geschiktvoorFractie"
                name="geschiktvoorFractie"
                data-cy="geschiktvoorFractie"
                label="Geschiktvoor Fractie"
                type="select"
              >
                <option value="" key="0" />
                {fracties
                  ? fracties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="container-soortContainertype"
                name="soortContainertype"
                data-cy="soortContainertype"
                label="Soort Containertype"
                type="select"
              >
                <option value="" key="0" />
                {containertypes
                  ? containertypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="container-heeftLocatie" name="heeftLocatie" data-cy="heeftLocatie" label="Heeft Locatie" type="select">
                <option value="" key="0" />
                {locaties
                  ? locaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/container" replace color="info">
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

export default ContainerUpdate;
