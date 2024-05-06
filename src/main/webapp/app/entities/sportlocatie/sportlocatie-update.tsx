import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { ISportvereniging } from 'app/shared/model/sportvereniging.model';
import { getEntities as getSportverenigings } from 'app/entities/sportvereniging/sportvereniging.reducer';
import { ISportlocatie } from 'app/shared/model/sportlocatie.model';
import { getEntity, updateEntity, createEntity, reset } from './sportlocatie.reducer';

export const SportlocatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const schools = useAppSelector(state => state.school.entities);
  const sportverenigings = useAppSelector(state => state.sportvereniging.entities);
  const sportlocatieEntity = useAppSelector(state => state.sportlocatie.entity);
  const loading = useAppSelector(state => state.sportlocatie.loading);
  const updating = useAppSelector(state => state.sportlocatie.updating);
  const updateSuccess = useAppSelector(state => state.sportlocatie.updateSuccess);

  const handleClose = () => {
    navigate('/sportlocatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSchools({}));
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
      ...sportlocatieEntity,
      ...values,
      gebruiktSchools: mapIdList(values.gebruiktSchools),
      gebruiktSportverenigings: mapIdList(values.gebruiktSportverenigings),
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
          ...sportlocatieEntity,
          gebruiktSchools: sportlocatieEntity?.gebruiktSchools?.map(e => e.id.toString()),
          gebruiktSportverenigings: sportlocatieEntity?.gebruiktSportverenigings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sportlocatie.home.createOrEditLabel" data-cy="SportlocatieCreateUpdateHeading">
            Create or edit a Sportlocatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sportlocatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="sportlocatie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Gebruikt School"
                id="sportlocatie-gebruiktSchool"
                data-cy="gebruiktSchool"
                type="select"
                multiple
                name="gebruiktSchools"
              >
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Gebruikt Sportvereniging"
                id="sportlocatie-gebruiktSportvereniging"
                data-cy="gebruiktSportvereniging"
                type="select"
                multiple
                name="gebruiktSportverenigings"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sportlocatie" replace color="info">
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

export default SportlocatieUpdate;
