import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITeam } from 'app/shared/model/team.model';
import { getEntities as getTeams } from 'app/entities/team/team.reducer';
import { ITraject } from 'app/shared/model/traject.model';
import { getEntities as getTrajects } from 'app/entities/traject/traject.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';
import { getEntity, updateEntity, createEntity, reset } from './clientbegeleider.reducer';

export const ClientbegeleiderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const teams = useAppSelector(state => state.team.entities);
  const trajects = useAppSelector(state => state.traject.entities);
  const clients = useAppSelector(state => state.client.entities);
  const clientbegeleiderEntity = useAppSelector(state => state.clientbegeleider.entity);
  const loading = useAppSelector(state => state.clientbegeleider.loading);
  const updating = useAppSelector(state => state.clientbegeleider.updating);
  const updateSuccess = useAppSelector(state => state.clientbegeleider.updateSuccess);

  const handleClose = () => {
    navigate('/clientbegeleider');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTeams({}));
    dispatch(getTrajects({}));
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
      ...clientbegeleiderEntity,
      ...values,
      maaktonderdeeluitvanTeam: teams.find(it => it.id.toString() === values.maaktonderdeeluitvanTeam?.toString()),
      begeleidtTraject: trajects.find(it => it.id.toString() === values.begeleidtTraject?.toString()),
      ondersteuntclientClients: mapIdList(values.ondersteuntclientClients),
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
          ...clientbegeleiderEntity,
          maaktonderdeeluitvanTeam: clientbegeleiderEntity?.maaktonderdeeluitvanTeam?.id,
          begeleidtTraject: clientbegeleiderEntity?.begeleidtTraject?.id,
          ondersteuntclientClients: clientbegeleiderEntity?.ondersteuntclientClients?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.clientbegeleider.home.createOrEditLabel" data-cy="ClientbegeleiderCreateUpdateHeading">
            Create or edit a Clientbegeleider
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
                <ValidatedField name="id" required readOnly id="clientbegeleider-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Begeleiderscode"
                id="clientbegeleider-begeleiderscode"
                name="begeleiderscode"
                data-cy="begeleiderscode"
                type="text"
              />
              <ValidatedField
                id="clientbegeleider-maaktonderdeeluitvanTeam"
                name="maaktonderdeeluitvanTeam"
                data-cy="maaktonderdeeluitvanTeam"
                label="Maaktonderdeeluitvan Team"
                type="select"
              >
                <option value="" key="0" />
                {teams
                  ? teams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="clientbegeleider-begeleidtTraject"
                name="begeleidtTraject"
                data-cy="begeleidtTraject"
                label="Begeleidt Traject"
                type="select"
              >
                <option value="" key="0" />
                {trajects
                  ? trajects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Ondersteuntclient Client"
                id="clientbegeleider-ondersteuntclientClient"
                data-cy="ondersteuntclientClient"
                type="select"
                multiple
                name="ondersteuntclientClients"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/clientbegeleider" replace color="info">
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

export default ClientbegeleiderUpdate;
