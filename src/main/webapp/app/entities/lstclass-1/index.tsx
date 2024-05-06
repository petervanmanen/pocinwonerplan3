import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lstclass1 from './lstclass-1';
import Lstclass1Detail from './lstclass-1-detail';
import Lstclass1Update from './lstclass-1-update';
import Lstclass1DeleteDialog from './lstclass-1-delete-dialog';

const Lstclass1Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Lstclass1 />} />
    <Route path="new" element={<Lstclass1Update />} />
    <Route path=":id">
      <Route index element={<Lstclass1Detail />} />
      <Route path="edit" element={<Lstclass1Update />} />
      <Route path="delete" element={<Lstclass1DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default Lstclass1Routes;
