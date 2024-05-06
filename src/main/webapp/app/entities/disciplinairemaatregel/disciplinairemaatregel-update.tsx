import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDisciplinairemaatregel } from 'app/shared/model/disciplinairemaatregel.model';
import { getEntity, updateEntity, createEntity, reset } from './disciplinairemaatregel.reducer';

export const DisciplinairemaatregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const disciplinairemaatregelEntity = useAppSelector(state => state.disciplinairemaatregel.entity);
  const loading = useAppSelector(state => state.disciplinairemaatregel.loading);
  const updating = useAppSelector(state => state.disciplinairemaatregel.updating);
  const updateSuccess = useAppSelector(state => state.disciplinairemaatregel.updateSuccess);

  const handleClose = () => {
    navigate('/disciplinairemaatregel');
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
      ...disciplinairemaatregelEntity,
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
          ...disciplinairemaatregelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.disciplinairemaatregel.home.createOrEditLabel" data-cy="DisciplinairemaatregelCreateUpdateHeading">
            Create or edit a Disciplinairemaatregel
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
                <ValidatedField name="id" required readOnly id="disciplinairemaatregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumgeconstateerd"
                id="disciplinairemaatregel-datumgeconstateerd"
                name="datumgeconstateerd"
                data-cy="datumgeconstateerd"
                type="date"
              />
              <ValidatedField
                label="Datumopgelegd"
                id="disciplinairemaatregel-datumopgelegd"
                name="datumopgelegd"
                data-cy="datumopgelegd"
                type="date"
              />
              <ValidatedField
                label="Omschrijving"
                id="disciplinairemaatregel-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Reden" id="disciplinairemaatregel-reden" name="reden" data-cy="reden" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/disciplinairemaatregel" replace color="info">
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

export default DisciplinairemaatregelUpdate;
