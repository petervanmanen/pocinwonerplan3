import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeperkingscategorie } from 'app/shared/model/beperkingscategorie.model';
import { getEntity, updateEntity, createEntity, reset } from './beperkingscategorie.reducer';

export const BeperkingscategorieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beperkingscategorieEntity = useAppSelector(state => state.beperkingscategorie.entity);
  const loading = useAppSelector(state => state.beperkingscategorie.loading);
  const updating = useAppSelector(state => state.beperkingscategorie.updating);
  const updateSuccess = useAppSelector(state => state.beperkingscategorie.updateSuccess);

  const handleClose = () => {
    navigate('/beperkingscategorie');
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
      ...beperkingscategorieEntity,
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
          ...beperkingscategorieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beperkingscategorie.home.createOrEditLabel" data-cy="BeperkingscategorieCreateUpdateHeading">
            Create or edit a Beperkingscategorie
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
                <ValidatedField name="id" required readOnly id="beperkingscategorie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Code"
                id="beperkingscategorie-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField label="Wet" id="beperkingscategorie-wet" name="wet" data-cy="wet" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beperkingscategorie" replace color="info">
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

export default BeperkingscategorieUpdate;
