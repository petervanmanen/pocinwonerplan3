import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanwezigedeelnemer } from 'app/shared/model/aanwezigedeelnemer.model';
import { getEntity, updateEntity, createEntity, reset } from './aanwezigedeelnemer.reducer';

export const AanwezigedeelnemerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanwezigedeelnemerEntity = useAppSelector(state => state.aanwezigedeelnemer.entity);
  const loading = useAppSelector(state => state.aanwezigedeelnemer.loading);
  const updating = useAppSelector(state => state.aanwezigedeelnemer.updating);
  const updateSuccess = useAppSelector(state => state.aanwezigedeelnemer.updateSuccess);

  const handleClose = () => {
    navigate('/aanwezigedeelnemer');
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
      ...aanwezigedeelnemerEntity,
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
          ...aanwezigedeelnemerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanwezigedeelnemer.home.createOrEditLabel" data-cy="AanwezigedeelnemerCreateUpdateHeading">
            Create or edit a Aanwezigedeelnemer
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
                <ValidatedField name="id" required readOnly id="aanwezigedeelnemer-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanvangaanwezigheid"
                id="aanwezigedeelnemer-aanvangaanwezigheid"
                name="aanvangaanwezigheid"
                data-cy="aanvangaanwezigheid"
                type="text"
              />
              <ValidatedField
                label="Eindeaanwezigheid"
                id="aanwezigedeelnemer-eindeaanwezigheid"
                name="eindeaanwezigheid"
                data-cy="eindeaanwezigheid"
                type="text"
              />
              <ValidatedField label="Naam" id="aanwezigedeelnemer-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Rol" id="aanwezigedeelnemer-rol" name="rol" data-cy="rol" type="text" />
              <ValidatedField
                label="Vertegenwoordigtorganisatie"
                id="aanwezigedeelnemer-vertegenwoordigtorganisatie"
                name="vertegenwoordigtorganisatie"
                data-cy="vertegenwoordigtorganisatie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanwezigedeelnemer" replace color="info">
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

export default AanwezigedeelnemerUpdate;
