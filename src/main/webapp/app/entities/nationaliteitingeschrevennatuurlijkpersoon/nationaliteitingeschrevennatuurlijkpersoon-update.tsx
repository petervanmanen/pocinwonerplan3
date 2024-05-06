import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INationaliteitingeschrevennatuurlijkpersoon } from 'app/shared/model/nationaliteitingeschrevennatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './nationaliteitingeschrevennatuurlijkpersoon.reducer';

export const NationaliteitingeschrevennatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nationaliteitingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.nationaliteitingeschrevennatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.nationaliteitingeschrevennatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.nationaliteitingeschrevennatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.nationaliteitingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/nationaliteitingeschrevennatuurlijkpersoon');
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
      ...nationaliteitingeschrevennatuurlijkpersoonEntity,
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
          ...nationaliteitingeschrevennatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.nationaliteitingeschrevennatuurlijkpersoon.home.createOrEditLabel"
            data-cy="NationaliteitingeschrevennatuurlijkpersoonCreateUpdateHeading"
          >
            Create or edit a Nationaliteitingeschrevennatuurlijkpersoon
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="nationaliteitingeschrevennatuurlijkpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Buitenlandspersoonsnummer"
                id="nationaliteitingeschrevennatuurlijkpersoon-buitenlandspersoonsnummer"
                name="buitenlandspersoonsnummer"
                data-cy="buitenlandspersoonsnummer"
                type="text"
              />
              <ValidatedField
                label="Nationaliteit"
                id="nationaliteitingeschrevennatuurlijkpersoon-nationaliteit"
                name="nationaliteit"
                data-cy="nationaliteit"
                type="text"
              />
              <ValidatedField
                label="Redenverkrijging"
                id="nationaliteitingeschrevennatuurlijkpersoon-redenverkrijging"
                name="redenverkrijging"
                data-cy="redenverkrijging"
                type="text"
              />
              <ValidatedField
                label="Redenverlies"
                id="nationaliteitingeschrevennatuurlijkpersoon-redenverlies"
                name="redenverlies"
                data-cy="redenverlies"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/nationaliteitingeschrevennatuurlijkpersoon"
                replace
                color="info"
              >
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

export default NationaliteitingeschrevennatuurlijkpersoonUpdate;
