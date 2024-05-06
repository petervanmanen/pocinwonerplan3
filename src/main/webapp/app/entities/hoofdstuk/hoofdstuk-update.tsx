import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPeriode } from 'app/shared/model/periode.model';
import { getEntities as getPeriodes } from 'app/entities/periode/periode.reducer';
import { IHoofdstuk } from 'app/shared/model/hoofdstuk.model';
import { getEntity, updateEntity, createEntity, reset } from './hoofdstuk.reducer';

export const HoofdstukUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const periodes = useAppSelector(state => state.periode.entities);
  const hoofdstukEntity = useAppSelector(state => state.hoofdstuk.entity);
  const loading = useAppSelector(state => state.hoofdstuk.loading);
  const updating = useAppSelector(state => state.hoofdstuk.updating);
  const updateSuccess = useAppSelector(state => state.hoofdstuk.updateSuccess);

  const handleClose = () => {
    navigate('/hoofdstuk');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPeriodes({}));
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
      ...hoofdstukEntity,
      ...values,
      binnenPeriodes: mapIdList(values.binnenPeriodes),
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
          ...hoofdstukEntity,
          binnenPeriodes: hoofdstukEntity?.binnenPeriodes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.hoofdstuk.home.createOrEditLabel" data-cy="HoofdstukCreateUpdateHeading">
            Create or edit a Hoofdstuk
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="hoofdstuk-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="hoofdstuk-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Nummer"
                id="hoofdstuk-nummer"
                name="nummer"
                data-cy="nummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="hoofdstuk-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Binnen Periode"
                id="hoofdstuk-binnenPeriode"
                data-cy="binnenPeriode"
                type="select"
                multiple
                name="binnenPeriodes"
              >
                <option value="" key="0" />
                {periodes
                  ? periodes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hoofdstuk" replace color="info">
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

export default HoofdstukUpdate;
