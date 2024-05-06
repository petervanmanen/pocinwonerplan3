import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWozdeelobjectcode } from 'app/shared/model/wozdeelobjectcode.model';
import { getEntity, updateEntity, createEntity, reset } from './wozdeelobjectcode.reducer';

export const WozdeelobjectcodeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wozdeelobjectcodeEntity = useAppSelector(state => state.wozdeelobjectcode.entity);
  const loading = useAppSelector(state => state.wozdeelobjectcode.loading);
  const updating = useAppSelector(state => state.wozdeelobjectcode.updating);
  const updateSuccess = useAppSelector(state => state.wozdeelobjectcode.updateSuccess);

  const handleClose = () => {
    navigate('/wozdeelobjectcode');
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
      ...wozdeelobjectcodeEntity,
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
          ...wozdeelobjectcodeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.wozdeelobjectcode.home.createOrEditLabel" data-cy="WozdeelobjectcodeCreateUpdateHeading">
            Create or edit a Wozdeelobjectcode
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
                <ValidatedField name="id" required readOnly id="wozdeelobjectcode-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheiddeelojectcode"
                id="wozdeelobjectcode-datumbegingeldigheiddeelojectcode"
                name="datumbegingeldigheiddeelojectcode"
                data-cy="datumbegingeldigheiddeelojectcode"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheiddeelobjectcode"
                id="wozdeelobjectcode-datumeindegeldigheiddeelobjectcode"
                name="datumeindegeldigheiddeelobjectcode"
                data-cy="datumeindegeldigheiddeelobjectcode"
                type="date"
              />
              <ValidatedField
                label="Deelobjectcode"
                id="wozdeelobjectcode-deelobjectcode"
                name="deelobjectcode"
                data-cy="deelobjectcode"
                type="text"
              />
              <ValidatedField
                label="Naamdeelobjectcode"
                id="wozdeelobjectcode-naamdeelobjectcode"
                name="naamdeelobjectcode"
                data-cy="naamdeelobjectcode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wozdeelobjectcode" replace color="info">
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

export default WozdeelobjectcodeUpdate;
