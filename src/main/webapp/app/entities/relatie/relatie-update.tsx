import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRelatiesoort } from 'app/shared/model/relatiesoort.model';
import { getEntities as getRelatiesoorts } from 'app/entities/relatiesoort/relatiesoort.reducer';
import { IHuishouden } from 'app/shared/model/huishouden.model';
import { getEntities as getHuishoudens } from 'app/entities/huishouden/huishouden.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IRelatie } from 'app/shared/model/relatie.model';
import { getEntity, updateEntity, createEntity, reset } from './relatie.reducer';

export const RelatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const relatiesoorts = useAppSelector(state => state.relatiesoort.entities);
  const huishoudens = useAppSelector(state => state.huishouden.entities);
  const clients = useAppSelector(state => state.client.entities);
  const relatieEntity = useAppSelector(state => state.relatie.entity);
  const loading = useAppSelector(state => state.relatie.loading);
  const updating = useAppSelector(state => state.relatie.updating);
  const updateSuccess = useAppSelector(state => state.relatie.updateSuccess);

  const handleClose = () => {
    navigate('/relatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRelatiesoorts({}));
    dispatch(getHuishoudens({}));
    dispatch(getClients({}));
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
      ...relatieEntity,
      ...values,
      issoortRelatiesoort: relatiesoorts.find(it => it.id.toString() === values.issoortRelatiesoort?.toString()),
      maaktonderdeelvanHuishoudens: mapIdList(values.maaktonderdeelvanHuishoudens),
      heeftrelatieClients: mapIdList(values.heeftrelatieClients),
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
          ...relatieEntity,
          issoortRelatiesoort: relatieEntity?.issoortRelatiesoort?.id,
          maaktonderdeelvanHuishoudens: relatieEntity?.maaktonderdeelvanHuishoudens?.map(e => e.id.toString()),
          heeftrelatieClients: relatieEntity?.heeftrelatieClients?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.relatie.home.createOrEditLabel" data-cy="RelatieCreateUpdateHeading">
            Create or edit a Relatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="relatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Relatiesoort" id="relatie-relatiesoort" name="relatiesoort" data-cy="relatiesoort" type="text" />
              <ValidatedField
                id="relatie-issoortRelatiesoort"
                name="issoortRelatiesoort"
                data-cy="issoortRelatiesoort"
                label="Issoort Relatiesoort"
                type="select"
              >
                <option value="" key="0" />
                {relatiesoorts
                  ? relatiesoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Maaktonderdeelvan Huishouden"
                id="relatie-maaktonderdeelvanHuishouden"
                data-cy="maaktonderdeelvanHuishouden"
                type="select"
                multiple
                name="maaktonderdeelvanHuishoudens"
              >
                <option value="" key="0" />
                {huishoudens
                  ? huishoudens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeftrelatie Client"
                id="relatie-heeftrelatieClient"
                data-cy="heeftrelatieClient"
                type="select"
                multiple
                name="heeftrelatieClients"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/relatie" replace color="info">
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

export default RelatieUpdate;
