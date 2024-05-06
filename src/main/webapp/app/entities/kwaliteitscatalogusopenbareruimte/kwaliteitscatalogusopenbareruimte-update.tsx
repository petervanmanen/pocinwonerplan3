import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKwaliteitscatalogusopenbareruimte } from 'app/shared/model/kwaliteitscatalogusopenbareruimte.model';
import { getEntity, updateEntity, createEntity, reset } from './kwaliteitscatalogusopenbareruimte.reducer';

export const KwaliteitscatalogusopenbareruimteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kwaliteitscatalogusopenbareruimteEntity = useAppSelector(state => state.kwaliteitscatalogusopenbareruimte.entity);
  const loading = useAppSelector(state => state.kwaliteitscatalogusopenbareruimte.loading);
  const updating = useAppSelector(state => state.kwaliteitscatalogusopenbareruimte.updating);
  const updateSuccess = useAppSelector(state => state.kwaliteitscatalogusopenbareruimte.updateSuccess);

  const handleClose = () => {
    navigate('/kwaliteitscatalogusopenbareruimte');
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
      ...kwaliteitscatalogusopenbareruimteEntity,
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
          ...kwaliteitscatalogusopenbareruimteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.kwaliteitscatalogusopenbareruimte.home.createOrEditLabel"
            data-cy="KwaliteitscatalogusopenbareruimteCreateUpdateHeading"
          >
            Create or edit a Kwaliteitscatalogusopenbareruimte
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
                  id="kwaliteitscatalogusopenbareruimte-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/kwaliteitscatalogusopenbareruimte"
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

export default KwaliteitscatalogusopenbareruimteUpdate;
