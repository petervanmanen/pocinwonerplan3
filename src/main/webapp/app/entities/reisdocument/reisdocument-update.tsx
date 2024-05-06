import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReisdocument } from 'app/shared/model/reisdocument.model';
import { getEntity, updateEntity, createEntity, reset } from './reisdocument.reducer';

export const ReisdocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const reisdocumentEntity = useAppSelector(state => state.reisdocument.entity);
  const loading = useAppSelector(state => state.reisdocument.loading);
  const updating = useAppSelector(state => state.reisdocument.updating);
  const updateSuccess = useAppSelector(state => state.reisdocument.updateSuccess);

  const handleClose = () => {
    navigate('/reisdocument');
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
      ...reisdocumentEntity,
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
          ...reisdocumentEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.reisdocument.home.createOrEditLabel" data-cy="ReisdocumentCreateUpdateHeading">
            Create or edit a Reisdocument
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="reisdocument-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aanduidinginhoudingvermissing"
                id="reisdocument-aanduidinginhoudingvermissing"
                name="aanduidinginhoudingvermissing"
                data-cy="aanduidinginhoudingvermissing"
                type="text"
              />
              <ValidatedField
                label="Autoriteitvanafgifte"
                id="reisdocument-autoriteitvanafgifte"
                name="autoriteitvanafgifte"
                data-cy="autoriteitvanafgifte"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheiddocument"
                id="reisdocument-datumeindegeldigheiddocument"
                name="datumeindegeldigheiddocument"
                data-cy="datumeindegeldigheiddocument"
                type="text"
              />
              <ValidatedField
                label="Datumingangdocument"
                id="reisdocument-datumingangdocument"
                name="datumingangdocument"
                data-cy="datumingangdocument"
                type="text"
              />
              <ValidatedField
                label="Datuminhoudingofvermissing"
                id="reisdocument-datuminhoudingofvermissing"
                name="datuminhoudingofvermissing"
                data-cy="datuminhoudingofvermissing"
                type="text"
              />
              <ValidatedField
                label="Datumuitgifte"
                id="reisdocument-datumuitgifte"
                name="datumuitgifte"
                data-cy="datumuitgifte"
                type="text"
              />
              <ValidatedField
                label="Reisdocumentnummer"
                id="reisdocument-reisdocumentnummer"
                name="reisdocumentnummer"
                data-cy="reisdocumentnummer"
                type="text"
              />
              <ValidatedField label="Soort" id="reisdocument-soort" name="soort" data-cy="soort" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reisdocument" replace color="info">
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

export default ReisdocumentUpdate;
