import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProducttype } from 'app/shared/model/producttype.model';
import { getEntities as getProducttypes } from 'app/entities/producttype/producttype.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IBedrijfsprocestype } from 'app/shared/model/bedrijfsprocestype.model';
import { getEntities as getBedrijfsprocestypes } from 'app/entities/bedrijfsprocestype/bedrijfsprocestype.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IFormuliersoort } from 'app/shared/model/formuliersoort.model';
import { getEntities as getFormuliersoorts } from 'app/entities/formuliersoort/formuliersoort.reducer';
import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { getEntity, updateEntity, createEntity, reset } from './zaaktype.reducer';

export const ZaaktypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const producttypes = useAppSelector(state => state.producttype.entities);
  const products = useAppSelector(state => state.product.entities);
  const bedrijfsprocestypes = useAppSelector(state => state.bedrijfsprocestype.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const formuliersoorts = useAppSelector(state => state.formuliersoort.entities);
  const zaaktypeEntity = useAppSelector(state => state.zaaktype.entity);
  const loading = useAppSelector(state => state.zaaktype.loading);
  const updating = useAppSelector(state => state.zaaktype.updating);
  const updateSuccess = useAppSelector(state => state.zaaktype.updateSuccess);

  const handleClose = () => {
    navigate('/zaaktype');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProducttypes({}));
    dispatch(getProducts({}));
    dispatch(getBedrijfsprocestypes({}));
    dispatch(getMedewerkers({}));
    dispatch(getFormuliersoorts({}));
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
      ...zaaktypeEntity,
      ...values,
      heeftProducttype: producttypes.find(it => it.id.toString() === values.heeftProducttype?.toString()),
      betreftProduct: products.find(it => it.id.toString() === values.betreftProduct?.toString()),
      heeftBedrijfsprocestype: bedrijfsprocestypes.find(it => it.id.toString() === values.heeftBedrijfsprocestype?.toString()),
      isverantwoordelijkevoorMedewerker: medewerkers.find(it => it.id.toString() === values.isverantwoordelijkevoorMedewerker?.toString()),
      isaanleidingvoorFormuliersoorts: mapIdList(values.isaanleidingvoorFormuliersoorts),
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
          ...zaaktypeEntity,
          heeftProducttype: zaaktypeEntity?.heeftProducttype?.id,
          betreftProduct: zaaktypeEntity?.betreftProduct?.id,
          heeftBedrijfsprocestype: zaaktypeEntity?.heeftBedrijfsprocestype?.id,
          isverantwoordelijkevoorMedewerker: zaaktypeEntity?.isverantwoordelijkevoorMedewerker?.id,
          isaanleidingvoorFormuliersoorts: zaaktypeEntity?.isaanleidingvoorFormuliersoorts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.zaaktype.home.createOrEditLabel" data-cy="ZaaktypeCreateUpdateHeading">
            Create or edit a Zaaktype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="zaaktype-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Archiefcode"
                id="zaaktype-archiefcode"
                name="archiefcode"
                data-cy="archiefcode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Datumbegingeldigheidzaaktype"
                id="zaaktype-datumbegingeldigheidzaaktype"
                name="datumbegingeldigheidzaaktype"
                data-cy="datumbegingeldigheidzaaktype"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheidzaaktype"
                id="zaaktype-datumeindegeldigheidzaaktype"
                name="datumeindegeldigheidzaaktype"
                data-cy="datumeindegeldigheidzaaktype"
                type="text"
              />
              <ValidatedField
                label="Doorlooptijdbehandeling"
                id="zaaktype-doorlooptijdbehandeling"
                name="doorlooptijdbehandeling"
                data-cy="doorlooptijdbehandeling"
                type="text"
              />
              <ValidatedField
                label="Indicatiepublicatie"
                id="zaaktype-indicatiepublicatie"
                name="indicatiepublicatie"
                data-cy="indicatiepublicatie"
                type="text"
              />
              <ValidatedField
                label="Publicatietekst"
                id="zaaktype-publicatietekst"
                name="publicatietekst"
                data-cy="publicatietekst"
                type="text"
              />
              <ValidatedField
                label="Servicenormbehandeling"
                id="zaaktype-servicenormbehandeling"
                name="servicenormbehandeling"
                data-cy="servicenormbehandeling"
                type="text"
              />
              <ValidatedField label="Trefwoord" id="zaaktype-trefwoord" name="trefwoord" data-cy="trefwoord" type="text" />
              <ValidatedField
                label="Vertrouwelijkaanduiding"
                id="zaaktype-vertrouwelijkaanduiding"
                name="vertrouwelijkaanduiding"
                data-cy="vertrouwelijkaanduiding"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Zaakcategorie" id="zaaktype-zaakcategorie" name="zaakcategorie" data-cy="zaakcategorie" type="text" />
              <ValidatedField
                label="Zaaktypeomschrijving"
                id="zaaktype-zaaktypeomschrijving"
                name="zaaktypeomschrijving"
                data-cy="zaaktypeomschrijving"
                type="text"
              />
              <ValidatedField
                label="Zaaktypeomschrijvinggeneriek"
                id="zaaktype-zaaktypeomschrijvinggeneriek"
                name="zaaktypeomschrijvinggeneriek"
                data-cy="zaaktypeomschrijvinggeneriek"
                type="text"
              />
              <ValidatedField
                id="zaaktype-heeftProducttype"
                name="heeftProducttype"
                data-cy="heeftProducttype"
                label="Heeft Producttype"
                type="select"
                required
              >
                <option value="" key="0" />
                {producttypes
                  ? producttypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="zaaktype-betreftProduct"
                name="betreftProduct"
                data-cy="betreftProduct"
                label="Betreft Product"
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="zaaktype-heeftBedrijfsprocestype"
                name="heeftBedrijfsprocestype"
                data-cy="heeftBedrijfsprocestype"
                label="Heeft Bedrijfsprocestype"
                type="select"
                required
              >
                <option value="" key="0" />
                {bedrijfsprocestypes
                  ? bedrijfsprocestypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="zaaktype-isverantwoordelijkevoorMedewerker"
                name="isverantwoordelijkevoorMedewerker"
                data-cy="isverantwoordelijkevoorMedewerker"
                label="Isverantwoordelijkevoor Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Isaanleidingvoor Formuliersoort"
                id="zaaktype-isaanleidingvoorFormuliersoort"
                data-cy="isaanleidingvoorFormuliersoort"
                type="select"
                multiple
                name="isaanleidingvoorFormuliersoorts"
              >
                <option value="" key="0" />
                {formuliersoorts
                  ? formuliersoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/zaaktype" replace color="info">
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

export default ZaaktypeUpdate;
