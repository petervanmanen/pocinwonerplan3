import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOnderwijsloopbaan } from 'app/shared/model/onderwijsloopbaan.model';
import { getEntities as getOnderwijsloopbaans } from 'app/entities/onderwijsloopbaan/onderwijsloopbaan.reducer';
import { IOnderwijssoort } from 'app/shared/model/onderwijssoort.model';
import { getEntities as getOnderwijssoorts } from 'app/entities/onderwijssoort/onderwijssoort.reducer';
import { ISportlocatie } from 'app/shared/model/sportlocatie.model';
import { getEntities as getSportlocaties } from 'app/entities/sportlocatie/sportlocatie.reducer';
import { ISchool } from 'app/shared/model/school.model';
import { getEntity, updateEntity, createEntity, reset } from './school.reducer';

export const SchoolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const onderwijsloopbaans = useAppSelector(state => state.onderwijsloopbaan.entities);
  const onderwijssoorts = useAppSelector(state => state.onderwijssoort.entities);
  const sportlocaties = useAppSelector(state => state.sportlocatie.entities);
  const schoolEntity = useAppSelector(state => state.school.entity);
  const loading = useAppSelector(state => state.school.loading);
  const updating = useAppSelector(state => state.school.updating);
  const updateSuccess = useAppSelector(state => state.school.updateSuccess);

  const handleClose = () => {
    navigate('/school');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOnderwijsloopbaans({}));
    dispatch(getOnderwijssoorts({}));
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
      ...schoolEntity,
      ...values,
      kentOnderwijsloopbaans: mapIdList(values.kentOnderwijsloopbaans),
      heeftOnderwijssoorts: mapIdList(values.heeftOnderwijssoorts),
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
          ...schoolEntity,
          kentOnderwijsloopbaans: schoolEntity?.kentOnderwijsloopbaans?.map(e => e.id.toString()),
          heeftOnderwijssoorts: schoolEntity?.heeftOnderwijssoorts?.map(e => e.id.toString()),
          gebruiktSportlocaties: schoolEntity?.gebruiktSportlocaties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.school.home.createOrEditLabel" data-cy="SchoolCreateUpdateHeading">
            Create or edit a School
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="school-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="school-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Kent Onderwijsloopbaan"
                id="school-kentOnderwijsloopbaan"
                data-cy="kentOnderwijsloopbaan"
                type="select"
                multiple
                name="kentOnderwijsloopbaans"
              >
                <option value="" key="0" />
                {onderwijsloopbaans
                  ? onderwijsloopbaans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Onderwijssoort"
                id="school-heeftOnderwijssoort"
                data-cy="heeftOnderwijssoort"
                type="select"
                multiple
                name="heeftOnderwijssoorts"
              >
                <option value="" key="0" />
                {onderwijssoorts
                  ? onderwijssoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Gebruikt Sportlocatie"
                id="school-gebruiktSportlocatie"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/school" replace color="info">
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

export default SchoolUpdate;
