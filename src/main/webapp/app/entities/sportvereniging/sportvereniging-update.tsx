import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISport } from 'app/shared/model/sport.model';
import { getEntities as getSports } from 'app/entities/sport/sport.reducer';
import { ISportlocatie } from 'app/shared/model/sportlocatie.model';
import { getEntities as getSportlocaties } from 'app/entities/sportlocatie/sportlocatie.reducer';
import { ISportvereniging } from 'app/shared/model/sportvereniging.model';
import { getEntity, updateEntity, createEntity, reset } from './sportvereniging.reducer';

export const SportverenigingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sports = useAppSelector(state => state.sport.entities);
  const sportlocaties = useAppSelector(state => state.sportlocatie.entities);
  const sportverenigingEntity = useAppSelector(state => state.sportvereniging.entity);
  const loading = useAppSelector(state => state.sportvereniging.loading);
  const updating = useAppSelector(state => state.sportvereniging.updating);
  const updateSuccess = useAppSelector(state => state.sportvereniging.updateSuccess);

  const handleClose = () => {
    navigate('/sportvereniging');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSports({}));
    dispatch(getSportlocaties({}));
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
      ...sportverenigingEntity,
      ...values,
      oefentuitSports: mapIdList(values.oefentuitSports),
      gebruiktSportlocaties: mapIdList(values.gebruiktSportlocaties),
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
          ...sportverenigingEntity,
          oefentuitSports: sportverenigingEntity?.oefentuitSports?.map(e => e.id.toString()),
          gebruiktSportlocaties: sportverenigingEntity?.gebruiktSportlocaties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sportvereniging.home.createOrEditLabel" data-cy="SportverenigingCreateUpdateHeading">
            Create or edit a Sportvereniging
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
                <ValidatedField name="id" required readOnly id="sportvereniging-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aantalnormteams"
                id="sportvereniging-aantalnormteams"
                name="aantalnormteams"
                data-cy="aantalnormteams"
                type="text"
              />
              <ValidatedField label="Adres" id="sportvereniging-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField
                label="Binnensport"
                id="sportvereniging-binnensport"
                name="binnensport"
                data-cy="binnensport"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Buitensport"
                id="sportvereniging-buitensport"
                name="buitensport"
                data-cy="buitensport"
                check
                type="checkbox"
              />
              <ValidatedField label="Email" id="sportvereniging-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Ledenaantal" id="sportvereniging-ledenaantal" name="ledenaantal" data-cy="ledenaantal" type="text" />
              <ValidatedField label="Naam" id="sportvereniging-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Typesport" id="sportvereniging-typesport" name="typesport" data-cy="typesport" type="text" />
              <ValidatedField
                label="Oefentuit Sport"
                id="sportvereniging-oefentuitSport"
                data-cy="oefentuitSport"
                type="select"
                multiple
                name="oefentuitSports"
              >
                <option value="" key="0" />
                {sports
                  ? sports.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Gebruikt Sportlocatie"
                id="sportvereniging-gebruiktSportlocatie"
                data-cy="gebruiktSportlocatie"
                type="select"
                multiple
                name="gebruiktSportlocaties"
              >
                <option value="" key="0" />
                {sportlocaties
                  ? sportlocaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sportvereniging" replace color="info">
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

export default SportverenigingUpdate;
