import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IValuta } from 'app/shared/model/valuta.model';
import { getEntity, updateEntity, createEntity, reset } from './valuta.reducer';

export const ValutaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const valutaEntity = useAppSelector(state => state.valuta.entity);
  const loading = useAppSelector(state => state.valuta.loading);
  const updating = useAppSelector(state => state.valuta.updating);
  const updateSuccess = useAppSelector(state => state.valuta.updateSuccess);

  const handleClose = () => {
    navigate('/valuta');
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
      ...valutaEntity,
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
          ...valutaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.valuta.home.createOrEditLabel" data-cy="ValutaCreateUpdateHeading">
            Create or edit a Valuta
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="valuta-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="valuta-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="valuta-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="text"
              />
              <ValidatedField label="Naam" id="valuta-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Valutacode" id="valuta-valutacode" name="valutacode" data-cy="valutacode" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/valuta" replace color="info">
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

export default ValutaUpdate;
