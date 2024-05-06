import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServer } from 'app/shared/model/server.model';
import { getEntities as getServers } from 'app/entities/server/server.reducer';
import { IDatabase } from 'app/shared/model/database.model';
import { getEntity, updateEntity, createEntity, reset } from './database.reducer';

export const DatabaseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const servers = useAppSelector(state => state.server.entities);
  const databaseEntity = useAppSelector(state => state.database.entity);
  const loading = useAppSelector(state => state.database.loading);
  const updating = useAppSelector(state => state.database.updating);
  const updateSuccess = useAppSelector(state => state.database.updateSuccess);

  const handleClose = () => {
    navigate('/database');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getServers({}));
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
      ...databaseEntity,
      ...values,
      servervandatabaseServer: servers.find(it => it.id.toString() === values.servervandatabaseServer?.toString()),
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
          ...databaseEntity,
          servervandatabaseServer: databaseEntity?.servervandatabaseServer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.database.home.createOrEditLabel" data-cy="DatabaseCreateUpdateHeading">
            Create or edit a Database
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="database-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Architectuur" id="database-architectuur" name="architectuur" data-cy="architectuur" type="text" />
              <ValidatedField label="Database" id="database-database" name="database" data-cy="database" type="text" />
              <ValidatedField
                label="Databaseversie"
                id="database-databaseversie"
                name="databaseversie"
                data-cy="databaseversie"
                type="text"
              />
              <ValidatedField label="Dbms" id="database-dbms" name="dbms" data-cy="dbms" type="text" />
              <ValidatedField label="Omschrijving" id="database-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Otap" id="database-otap" name="otap" data-cy="otap" type="text" />
              <ValidatedField label="Vlan" id="database-vlan" name="vlan" data-cy="vlan" type="text" />
              <ValidatedField
                id="database-servervandatabaseServer"
                name="servervandatabaseServer"
                data-cy="servervandatabaseServer"
                label="Servervandatabase Server"
                type="select"
              >
                <option value="" key="0" />
                {servers
                  ? servers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/database" replace color="info">
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

export default DatabaseUpdate;
