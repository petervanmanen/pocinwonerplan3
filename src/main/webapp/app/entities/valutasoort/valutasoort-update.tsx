import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IValutasoort } from 'app/shared/model/valutasoort.model';
import { getEntity, updateEntity, createEntity, reset } from './valutasoort.reducer';

export const ValutasoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const valutasoortEntity = useAppSelector(state => state.valutasoort.entity);
  const loading = useAppSelector(state => state.valutasoort.loading);
  const updating = useAppSelector(state => state.valutasoort.updating);
  const updateSuccess = useAppSelector(state => state.valutasoort.updateSuccess);

  const handleClose = () => {
    navigate('/valutasoort');
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
      ...valutasoortEntity,
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
          ...valutasoortEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.valutasoort.home.createOrEditLabel" data-cy="ValutasoortCreateUpdateHeading">
            Create or edit a Valutasoort
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="valutasoort-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheidvalutasoort"
                id="valutasoort-datumbegingeldigheidvalutasoort"
                name="datumbegingeldigheidvalutasoort"
                data-cy="datumbegingeldigheidvalutasoort"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidvalutasoort"
                id="valutasoort-datumeindegeldigheidvalutasoort"
                name="datumeindegeldigheidvalutasoort"
                data-cy="datumeindegeldigheidvalutasoort"
                type="date"
              />
              <ValidatedField label="Naamvaluta" id="valutasoort-naamvaluta" name="naamvaluta" data-cy="naamvaluta" type="text" />
              <ValidatedField label="Valutacode" id="valutasoort-valutacode" name="valutacode" data-cy="valutacode" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/valutasoort" replace color="info">
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

export default ValutasoortUpdate;
