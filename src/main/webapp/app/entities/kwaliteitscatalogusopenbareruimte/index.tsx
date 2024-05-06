import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kwaliteitscatalogusopenbareruimte from './kwaliteitscatalogusopenbareruimte';
import KwaliteitscatalogusopenbareruimteDetail from './kwaliteitscatalogusopenbareruimte-detail';
import KwaliteitscatalogusopenbareruimteUpdate from './kwaliteitscatalogusopenbareruimte-update';
import KwaliteitscatalogusopenbareruimteDeleteDialog from './kwaliteitscatalogusopenbareruimte-delete-dialog';

const KwaliteitscatalogusopenbareruimteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kwaliteitscatalogusopenbareruimte />} />
    <Route path="new" element={<KwaliteitscatalogusopenbareruimteUpdate />} />
    <Route path=":id">
      <Route index element={<KwaliteitscatalogusopenbareruimteDetail />} />
      <Route path="edit" element={<KwaliteitscatalogusopenbareruimteUpdate />} />
      <Route path="delete" element={<KwaliteitscatalogusopenbareruimteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KwaliteitscatalogusopenbareruimteRoutes;
