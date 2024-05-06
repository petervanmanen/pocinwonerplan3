import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Formulierverlenginginhuur from './formulierverlenginginhuur';
import FormulierverlenginginhuurDetail from './formulierverlenginginhuur-detail';
import FormulierverlenginginhuurUpdate from './formulierverlenginginhuur-update';
import FormulierverlenginginhuurDeleteDialog from './formulierverlenginginhuur-delete-dialog';

const FormulierverlenginginhuurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Formulierverlenginginhuur />} />
    <Route path="new" element={<FormulierverlenginginhuurUpdate />} />
    <Route path=":id">
      <Route index element={<FormulierverlenginginhuurDetail />} />
      <Route path="edit" element={<FormulierverlenginginhuurUpdate />} />
      <Route path="delete" element={<FormulierverlenginginhuurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FormulierverlenginginhuurRoutes;
