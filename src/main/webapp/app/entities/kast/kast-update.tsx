import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStelling } from 'app/shared/model/stelling.model';
import { getEntities as getStellings } from 'app/entities/stelling/stelling.reducer';
import { IKast } from 'app/shared/model/kast.model';
import { getEntity, updateEntity, createEntity, reset } from './kast.reducer';

export const KastUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const stellings = useAppSelector(state => state.stelling.entities);
  const kastEntity = useAppSelector(state => state.kast.entity);
  const loading = useAppSelector(state => state.kast.loading);
  const updating = useAppSelector(state => state.kast.updating);
  const updateSuccess = useAppSelector(state => state.kast.updateSuccess);

  const handleClose = () => {
    navigate('/kast');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStellings({}));
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
      ...kastEntity,
      ...values,
      heeftStelling: stellings.find(it => it.id.toString() === values.heeftStelling?.toString()),
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
          ...kastEntity,
          heeftStelling: kastEntity?.heeftStelling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kast.home.createOrEditLabel" data-cy="KastCreateUpdateHeading">
            Create or edit a Kast
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kast-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Kastnummer"
                id="kast-kastnummer"
                name="kastnummer"
                data-cy="kastnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField id="kast-heeftStelling" name="heeftStelling" data-cy="heeftStelling" label="Heeft Stelling" type="select">
                <option value="" key="0" />
                {stellings
                  ? stellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kast" replace color="info">
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

export default KastUpdate;
