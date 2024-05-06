import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDocumenttype } from 'app/shared/model/documenttype.model';
import { getEntity, updateEntity, createEntity, reset } from './documenttype.reducer';

export const DocumenttypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const documenttypeEntity = useAppSelector(state => state.documenttype.entity);
  const loading = useAppSelector(state => state.documenttype.loading);
  const updating = useAppSelector(state => state.documenttype.updating);
  const updateSuccess = useAppSelector(state => state.documenttype.updateSuccess);

  const handleClose = () => {
    navigate('/documenttype');
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
      ...documenttypeEntity,
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
          ...documenttypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.documenttype.home.createOrEditLabel" data-cy="DocumenttypeCreateUpdateHeading">
            Create or edit a Documenttype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="documenttype-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheiddocumenttype"
                id="documenttype-datumbegingeldigheiddocumenttype"
                name="datumbegingeldigheiddocumenttype"
                data-cy="datumbegingeldigheiddocumenttype"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheiddocumenttype"
                id="documenttype-datumeindegeldigheiddocumenttype"
                name="datumeindegeldigheiddocumenttype"
                data-cy="datumeindegeldigheiddocumenttype"
                type="text"
              />
              <ValidatedField
                label="Documentcategorie"
                id="documenttype-documentcategorie"
                name="documentcategorie"
                data-cy="documentcategorie"
                type="text"
              />
              <ValidatedField
                label="Documenttypeomschrijving"
                id="documenttype-documenttypeomschrijving"
                name="documenttypeomschrijving"
                data-cy="documenttypeomschrijving"
                type="text"
              />
              <ValidatedField
                label="Documenttypeomschrijvinggeneriek"
                id="documenttype-documenttypeomschrijvinggeneriek"
                name="documenttypeomschrijvinggeneriek"
                data-cy="documenttypeomschrijvinggeneriek"
                type="text"
              />
              <ValidatedField
                label="Documenttypetrefwoord"
                id="documenttype-documenttypetrefwoord"
                name="documenttypetrefwoord"
                data-cy="documenttypetrefwoord"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/documenttype" replace color="info">
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

export default DocumenttypeUpdate;
