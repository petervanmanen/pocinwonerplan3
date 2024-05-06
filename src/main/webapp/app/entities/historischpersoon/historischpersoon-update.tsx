import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHistorischpersoon } from 'app/shared/model/historischpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './historischpersoon.reducer';

export const HistorischpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const historischpersoonEntity = useAppSelector(state => state.historischpersoon.entity);
  const loading = useAppSelector(state => state.historischpersoon.loading);
  const updating = useAppSelector(state => state.historischpersoon.updating);
  const updateSuccess = useAppSelector(state => state.historischpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/historischpersoon');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...historischpersoonEntity,
      ...values,
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
          ...historischpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.historischpersoon.home.createOrEditLabel" data-cy="HistorischpersoonCreateUpdateHeading">
            Create or edit a Historischpersoon
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
                <ValidatedField name="id" required readOnly id="historischpersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Beroep" id="historischpersoon-beroep" name="beroep" data-cy="beroep" type="text" />
              <ValidatedField
                label="Datumgeboorte"
                id="historischpersoon-datumgeboorte"
                name="datumgeboorte"
                data-cy="datumgeboorte"
                type="date"
              />
              <ValidatedField
                label="Datumoverlijden"
                id="historischpersoon-datumoverlijden"
                name="datumoverlijden"
                data-cy="datumoverlijden"
                type="date"
              />
              <ValidatedField label="Naam" id="historischpersoon-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="historischpersoon-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Publiektoegankelijk"
                id="historischpersoon-publiektoegankelijk"
                name="publiektoegankelijk"
                data-cy="publiektoegankelijk"
                type="text"
              />
              <ValidatedField label="Woondeop" id="historischpersoon-woondeop" name="woondeop" data-cy="woondeop" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/historischpersoon" replace color="info">
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

export default HistorischpersoonUpdate;
