import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContainer } from 'app/shared/model/container.model';
import { getEntities as getContainers } from 'app/entities/container/container.reducer';
import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IRit } from 'app/shared/model/rit.model';
import { getEntities as getRits } from 'app/entities/rit/rit.reducer';
import { IOphaalmoment } from 'app/shared/model/ophaalmoment.model';
import { getEntity, updateEntity, createEntity, reset } from './ophaalmoment.reducer';

export const OphaalmomentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const containers = useAppSelector(state => state.container.entities);
  const locaties = useAppSelector(state => state.locatie.entities);
  const rits = useAppSelector(state => state.rit.entities);
  const ophaalmomentEntity = useAppSelector(state => state.ophaalmoment.entity);
  const loading = useAppSelector(state => state.ophaalmoment.loading);
  const updating = useAppSelector(state => state.ophaalmoment.updating);
  const updateSuccess = useAppSelector(state => state.ophaalmoment.updateSuccess);

  const handleClose = () => {
    navigate('/ophaalmoment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContainers({}));
    dispatch(getLocaties({}));
    dispatch(getRits({}));
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
      ...ophaalmomentEntity,
      ...values,
      gelostContainer: containers.find(it => it.id.toString() === values.gelostContainer?.toString()),
      gestoptopLocatie: locaties.find(it => it.id.toString() === values.gestoptopLocatie?.toString()),
      heeftRit: rits.find(it => it.id.toString() === values.heeftRit?.toString()),
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
          ...ophaalmomentEntity,
          gelostContainer: ophaalmomentEntity?.gelostContainer?.id,
          gestoptopLocatie: ophaalmomentEntity?.gestoptopLocatie?.id,
          heeftRit: ophaalmomentEntity?.heeftRit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ophaalmoment.home.createOrEditLabel" data-cy="OphaalmomentCreateUpdateHeading">
            Create or edit a Ophaalmoment
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ophaalmoment-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Gewichtstoename"
                id="ophaalmoment-gewichtstoename"
                name="gewichtstoename"
                data-cy="gewichtstoename"
                type="text"
              />
              <ValidatedField label="Tijdstip" id="ophaalmoment-tijdstip" name="tijdstip" data-cy="tijdstip" type="text" />
              <ValidatedField
                id="ophaalmoment-gelostContainer"
                name="gelostContainer"
                data-cy="gelostContainer"
                label="Gelost Container"
                type="select"
              >
                <option value="" key="0" />
                {containers
                  ? containers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="ophaalmoment-gestoptopLocatie"
                name="gestoptopLocatie"
                data-cy="gestoptopLocatie"
                label="Gestoptop Locatie"
                type="select"
              >
                <option value="" key="0" />
                {locaties
                  ? locaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="ophaalmoment-heeftRit" name="heeftRit" data-cy="heeftRit" label="Heeft Rit" type="select">
                <option value="" key="0" />
                {rits
                  ? rits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ophaalmoment" replace color="info">
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

export default OphaalmomentUpdate;
