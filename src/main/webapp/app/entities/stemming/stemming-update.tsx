import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { getEntities as getRaadsstuks } from 'app/entities/raadsstuk/raadsstuk.reducer';
import { IAgendapunt } from 'app/shared/model/agendapunt.model';
import { getEntities as getAgendapunts } from 'app/entities/agendapunt/agendapunt.reducer';
import { IStemming } from 'app/shared/model/stemming.model';
import { getEntity, updateEntity, createEntity, reset } from './stemming.reducer';

export const StemmingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const raadsstuks = useAppSelector(state => state.raadsstuk.entities);
  const agendapunts = useAppSelector(state => state.agendapunt.entities);
  const stemmingEntity = useAppSelector(state => state.stemming.entity);
  const loading = useAppSelector(state => state.stemming.loading);
  const updating = useAppSelector(state => state.stemming.updating);
  const updateSuccess = useAppSelector(state => state.stemming.updateSuccess);

  const handleClose = () => {
    navigate('/stemming');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRaadsstuks({}));
    dispatch(getAgendapunts({}));
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
      ...stemmingEntity,
      ...values,
      betreftRaadsstuk: raadsstuks.find(it => it.id.toString() === values.betreftRaadsstuk?.toString()),
      hoortbijAgendapunt: agendapunts.find(it => it.id.toString() === values.hoortbijAgendapunt?.toString()),
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
          ...stemmingEntity,
          betreftRaadsstuk: stemmingEntity?.betreftRaadsstuk?.id,
          hoortbijAgendapunt: stemmingEntity?.hoortbijAgendapunt?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.stemming.home.createOrEditLabel" data-cy="StemmingCreateUpdateHeading">
            Create or edit a Stemming
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="stemming-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Resultaat" id="stemming-resultaat" name="resultaat" data-cy="resultaat" type="text" />
              <ValidatedField label="Stemmingstype" id="stemming-stemmingstype" name="stemmingstype" data-cy="stemmingstype" type="text" />
              <ValidatedField
                id="stemming-betreftRaadsstuk"
                name="betreftRaadsstuk"
                data-cy="betreftRaadsstuk"
                label="Betreft Raadsstuk"
                type="select"
              >
                <option value="" key="0" />
                {raadsstuks
                  ? raadsstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="stemming-hoortbijAgendapunt"
                name="hoortbijAgendapunt"
                data-cy="hoortbijAgendapunt"
                label="Hoortbij Agendapunt"
                type="select"
              >
                <option value="" key="0" />
                {agendapunts
                  ? agendapunts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/stemming" replace color="info">
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

export default StemmingUpdate;
