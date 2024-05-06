import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISportvereniging } from 'app/shared/model/sportvereniging.model';
import { getEntities as getSportverenigings } from 'app/entities/sportvereniging/sportvereniging.reducer';
import { ISport } from 'app/shared/model/sport.model';
import { getEntity, updateEntity, createEntity, reset } from './sport.reducer';

export const SportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sportverenigings = useAppSelector(state => state.sportvereniging.entities);
  const sportEntity = useAppSelector(state => state.sport.entity);
  const loading = useAppSelector(state => state.sport.loading);
  const updating = useAppSelector(state => state.sport.updating);
  const updateSuccess = useAppSelector(state => state.sport.updateSuccess);

  const handleClose = () => {
    navigate('/sport');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSportverenigings({}));
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
      ...sportEntity,
      ...values,
      oefentuitSportverenigings: mapIdList(values.oefentuitSportverenigings),
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
          ...sportEntity,
          oefentuitSportverenigings: sportEntity?.oefentuitSportverenigings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sport.home.createOrEditLabel" data-cy="SportCreateUpdateHeading">
            Create or edit a Sport
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sport-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="sport-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Oefentuit Sportvereniging"
                id="sport-oefentuitSportvereniging"
                data-cy="oefentuitSportvereniging"
                type="select"
                multiple
                name="oefentuitSportverenigings"
              >
                <option value="" key="0" />
                {sportverenigings
                  ? sportverenigings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sport" replace color="info">
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

export default SportUpdate;
