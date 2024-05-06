import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeefgebied } from 'app/shared/model/leefgebied.model';
import { getEntities as getLeefgebieds } from 'app/entities/leefgebied/leefgebied.reducer';
import { IScoresoort } from 'app/shared/model/scoresoort.model';
import { getEntities as getScoresoorts } from 'app/entities/scoresoort/scoresoort.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IScore } from 'app/shared/model/score.model';
import { getEntity, updateEntity, createEntity, reset } from './score.reducer';

export const ScoreUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leefgebieds = useAppSelector(state => state.leefgebied.entities);
  const scoresoorts = useAppSelector(state => state.scoresoort.entities);
  const clients = useAppSelector(state => state.client.entities);
  const scoreEntity = useAppSelector(state => state.score.entity);
  const loading = useAppSelector(state => state.score.loading);
  const updating = useAppSelector(state => state.score.updating);
  const updateSuccess = useAppSelector(state => state.score.updateSuccess);

  const handleClose = () => {
    navigate('/score');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeefgebieds({}));
    dispatch(getScoresoorts({}));
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
      ...scoreEntity,
      ...values,
      scorebijleeggebiedLeefgebied: leefgebieds.find(it => it.id.toString() === values.scorebijleeggebiedLeefgebied?.toString()),
      hoogtescoreScoresoort: scoresoorts.find(it => it.id.toString() === values.hoogtescoreScoresoort?.toString()),
      heeftClient: clients.find(it => it.id.toString() === values.heeftClient?.toString()),
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
          ...scoreEntity,
          scorebijleeggebiedLeefgebied: scoreEntity?.scorebijleeggebiedLeefgebied?.id,
          hoogtescoreScoresoort: scoreEntity?.hoogtescoreScoresoort?.id,
          heeftClient: scoreEntity?.heeftClient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.score.home.createOrEditLabel" data-cy="ScoreCreateUpdateHeading">
            Create or edit a Score
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="score-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="score-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField
                id="score-scorebijleeggebiedLeefgebied"
                name="scorebijleeggebiedLeefgebied"
                data-cy="scorebijleeggebiedLeefgebied"
                label="Scorebijleeggebied Leefgebied"
                type="select"
              >
                <option value="" key="0" />
                {leefgebieds
                  ? leefgebieds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="score-hoogtescoreScoresoort"
                name="hoogtescoreScoresoort"
                data-cy="hoogtescoreScoresoort"
                label="Hoogtescore Scoresoort"
                type="select"
              >
                <option value="" key="0" />
                {scoresoorts
                  ? scoresoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="score-heeftClient" name="heeftClient" data-cy="heeftClient" label="Heeft Client" type="select">
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/score" replace color="info">
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

export default ScoreUpdate;
